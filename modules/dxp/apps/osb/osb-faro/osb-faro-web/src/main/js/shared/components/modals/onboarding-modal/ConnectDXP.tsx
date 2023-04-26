import * as API from 'shared/api';
import BaseScreen from './BaseScreen';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import CopyButton from 'shared/components/CopyButton';
import DataSourceQuery, {
	DataSource,
	DataSourceData,
	DataSourceSyncData
} from 'shared/queries/DataSourceQuery';
import getCN from 'classnames';
import Icon from 'shared/components/Icon';
import InfoPopover from 'shared/components/InfoPopover';
import Input from 'shared/components/Input';
import Label from 'shared/components/form/Label';
import Modal from 'shared/components/modal';
import React, {FC, useEffect, useRef, useState} from 'react';
import Select from 'shared/components/Select';
import StampLabel from 'shared/components/Label';
import URLConstants from 'shared/util/url-constants';
import {ActionType, useChannelContext} from 'shared/context/channel';
import {compose} from 'redux';
import {connect} from 'react-redux';
import {CREATE_DATE} from 'shared/util/pagination';
import {
	CredentialTypes,
	DataSourceTypes,
	OrderByDirections
} from 'shared/util/constants';
import {fetchDataSource} from 'shared/actions/data-sources';
import {get, noop, upperFirst} from 'lodash';
import {getDefaultChannel} from 'shared/components/channels-menu';
import {Routes, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {useInterval} from 'shared/hooks/useInterval';
import {useLazyQuery} from '@apollo/react-hooks';
import {withHistory} from 'shared/hoc';

const TIMEOUT_INTERVAL = 5000;

const DXP_VERSIONS = {
	'dxp-70-fixpack-98': {
		label: 'DXP 7.0 Fix Pack 98',
		url: URLConstants.DownloadDXP70FixPack98
	},
	'dxp-71-fixpack-22': {
		label: 'DXP 7.1 Fix Pack 22',
		url: URLConstants.DownloadDXP71FixPack22
	},
	'dxp-72-fixpack-1': {
		label: 'DXP 7.2 Fix Pack 11',
		url: URLConstants.DownloadDXP72FixPack11
	},
	'dxp-73-fixpack-1': {
		label: 'DXP 7.3 Fix Pack 1',
		url: URLConstants.DownloadDXP73FixPack1
	}
};

const DATA_SOURCE_STATUSES = {
	CONFIGURED: {
		display: StampLabel.Displays.Success,
		label: Liferay.Language.get('configured')
	},
	UNCONFIGURED: {
		display: StampLabel.Displays.Secondary,
		label: Liferay.Language.get('unconfigured')
	}
};

interface IConnectDXPProps {
	dxpConnected: boolean;
	groupId: string;
	onboarding?: boolean;
	onClose: () => void;
	onNext?: (increment?: number) => void;
}

interface IConnectDXPWrapperProps {
	dataSourceId?: string;
	fetchDataSource: ({
		groupId,
		id
	}: {
		groupId: string;
		id: string;
	}) => DataSource;
	history: {
		push: (path: string) => void;
	};
	isUpgrading: boolean;
	onDxpConnected: (dxpConnected: boolean) => void;
	onPrevious?: () => void;
}

interface IFooterProps {
	data: DataSourceData;
}

interface ITokenInputProps {
	token: string;
}

const ConnectDXP: React.FC<IConnectDXPWrapperProps & IConnectDXPProps> = ({
	dataSourceId,
	dxpConnected,
	fetchDataSource,
	groupId,
	history,
	onboarding,
	onClose,
	onDxpConnected,
	onNext
}) => {
	const {channelDispatch} = useChannelContext();
	const [token, setToken] = useState<string>('');

	const [getDataSources, {data}] = useLazyQuery<DataSourceData>(
		DataSourceQuery,
		{
			fetchPolicy: 'network-only',
			onCompleted: () => {
				onDxpConnected(true);
			},
			variables: {
				credentialsType: CredentialTypes.Token,
				size: 1,
				sort: {
					column: CREATE_DATE,
					type: OrderByDirections.Descending
				},
				type: DataSourceTypes.Liferay
			}
		}
	);

	let _tokenRequest;

	const getNextToken: (prevToken?: string) => Promise<any> = prevToken =>
		API.dataSource
			.fetchToken(groupId, dataSourceId)
			.then(nextToken => {
				if (!prevToken || prevToken === nextToken) {
					_tokenRequest = setTimeout(
						() => getNextToken(nextToken),
						TIMEOUT_INTERVAL
					);
				} else {
					analytics.track(
						'Established connection w/ DXP - TEST',
						null,
						{
							ip: '0'
						}
					);

					if (onboarding) {
						onDxpConnected(true);

						updateChannels();
						// if it's an upgrade from oauth to token, we need to fetch the DataSource
					} else if (dataSourceId) {
						fetchDataSource({groupId, id: dataSourceId});
					}

					getDataSources();
				}

				return nextToken;
			})
			.catch(err => {
				if (!err.IS_CANCELLATION_ERROR) {
					_tokenRequest = setTimeout(
						() => getNextToken(prevToken),
						TIMEOUT_INTERVAL
					);
				}

				return prevToken;
			});

	const updateChannels = () => {
		API.channels.fetchAll({groupId}).then(({items}) => {
			const channelId = get(items, [0, 'id']);

			history.push(toRoute(Routes.SITES, {channelId, groupId}));

			channelDispatch({
				payload: getDefaultChannel(channelId, items),
				type: ActionType.setSelectedChannel
			});

			channelDispatch({
				payload: items,
				type: ActionType.setChannels
			});
		});
	};

	useEffect(() => {
		_tokenRequest = getNextToken().then(setToken);

		return () => {
			clearTimeout(_tokenRequest);
		};
	}, []);

	return (
		<BaseScreen className='connect-dxp' onClose={onClose}>
			<Modal.Body className='d-flex flex-column align-items-center flex-grow-1 justify-content-center'>
				<div className='analytics-to-dxp-container'>
					<Icon size='xl' symbol='dxp-icon' />

					<Icon
						className={getCN('arrows', {connected: dxpConnected})}
						size='lg'
						symbol='ac-horizontal-arrows'
					/>

					<Icon
						size='xl'
						symbol={dxpConnected ? 'ac-logo' : 'ac-logo-grayscale'}
					/>
				</div>

				<span className='title connect-to-dxp d-flex justify-content-center'>
					{dxpConnected
						? Liferay.Language.get(
								'your-dxp-instance-is-connected-to-analytics-cloud'
						  )
						: Liferay.Language.get('connect-your-dxp-analytics')}
				</span>

				{!dxpConnected && (
					<>
						<TokenInput token={token} />

						<FixPackSelect />
					</>
				)}

				{dxpConnected && <DxpSyncTable />}
			</Modal.Body>

			<Footer
				data={data}
				dxpConnected={dxpConnected}
				groupId={groupId}
				onboarding={onboarding}
				onClose={onClose}
				onNext={onNext}
			/>
		</BaseScreen>
	);
};

const DxpSyncTable: FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [dataSource, setDataSources] = useState<DataSource>({
		contactsSyncDetails: {selected: false},
		sitesSyncDetails: {selected: false}
	});
	const [getDataSources, {data}] = useLazyQuery<DataSourceSyncData>(
		DataSourceQuery,
		{
			fetchPolicy: 'network-only',
			variables: {
				credentialsType: CredentialTypes.Token,
				size: 1,
				sort: {
					column: CREATE_DATE,
					type: OrderByDirections.Descending
				},
				type: DataSourceTypes.Liferay
			}
		}
	);
	useInterval<void>(getDataSources, TIMEOUT_INTERVAL);

	const getLabelProps = (selected: boolean) =>
		selected
			? DATA_SOURCE_STATUSES.CONFIGURED
			: DATA_SOURCE_STATUSES.UNCONFIGURED;

	const {display: contactsDisplay, label: contactslabel} = getLabelProps(
		dataSource?.contactsSyncDetails?.selected
	);

	const {display: sitesDisplay, label: sitesLabel} = getLabelProps(
		dataSource?.sitesSyncDetails?.selected
	);

	useEffect(() => {
		if (data) {
			setDataSources(data.dataSources[0]);
		}
	}, [data]);

	return (
		<div className='success-info w-100'>
			<div className='container'>
				<div className='row'>
					<span className='col'>{Liferay.Language.get('sites')}</span>

					<div className='col'>
						<StampLabel display={sitesDisplay}>
							{sitesLabel}
						</StampLabel>
					</div>
				</div>

				<div className='row'>
					<span className='col'>
						{Liferay.Language.get('contacts')}
					</span>

					<div className='col'>
						<StampLabel display={contactsDisplay}>
							{contactslabel}
						</StampLabel>
					</div>
				</div>
			</div>

			<div className='secondary-description mt-1 w-100'>
				{Liferay.Language.get(
					'you-can-check-your-data-source-syncing-status-under-settings-in-data-sources,-or-continue-configuring-your-data-source-on-your-dxp-instance'
				)}
			</div>
		</div>
	);
};

const Footer: FC<IFooterProps & IConnectDXPProps> = ({
	data,
	dxpConnected,
	groupId,
	onboarding,
	onClose,
	onNext
}) => {
	const getNavHref = () => {
		const id = get(data, ['dataSources', 0, 'id'], null);

		if (id) {
			return toRoute(Routes.SETTINGS_DATA_SOURCE, {groupId, id});
		}

		return toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {groupId});
	};

	return (
		<Modal.Footer className='d-flex justify-content-end'>
			<div>
				{!(dxpConnected && onboarding) && (
					<ClayButton
						className='button-root'
						disabled={dxpConnected}
						displayType='secondary'
						onClick={onboarding ? () => onNext() : onClose}
					>
						{onboarding
							? Liferay.Language.get('skip')
							: Liferay.Language.get('cancel')}
					</ClayButton>
				)}

				{!dxpConnected || onboarding ? (
					<ClayButton
						className='button-root'
						disabled={!dxpConnected}
						displayType='primary'
						onClick={onboarding ? () => onNext() : onClose}
					>
						{Liferay.Language.get('next')}
					</ClayButton>
				) : (
					<ClayLink
						button
						className='button-root'
						displayType='primary'
						href={getNavHref()}
						onClick={() => onClose()}
					>
						{Liferay.Language.get('done')}
					</ClayLink>
				)}
			</div>
		</Modal.Footer>
	);
};

const FixPackSelect: FC<React.HTMLAttributes<HTMLElement>> = () => {
	const dxpVersionsList = Object.keys(DXP_VERSIONS);
	const [dxpVersion, setDxpVersion] = useState<string>(dxpVersionsList[0]);

	return (
		<>
			<div className='secondary-description w-100 ml-6 mt-1'>
				{sub(
					Liferay.Language.get(
						'x-to-learn-how-to-connect-liferay-dxp-to-analytics-cloud'
					),
					[
						<a
							href={URLConstants.HelpConnectDxp}
							key='helpConnectDxpText'
							target='_blank'
						>
							{upperFirst(
								Liferay.Language.get('click-here').toLowerCase()
							)}
						</a>
					],
					false
				)}
			</div>

			<div className='fix-pack-container'>
				<div className='fix-pack-select'>
					<Label>
						{Liferay.Language.get('dxp-fix-pack-requirements')}

						<InfoPopover
							className='ml-2'
							content={Liferay.Language.get(
								'minimum-fix-pack-version-required-for-full-functionality'
							)}
							title={Liferay.Language.get(
								'dxp-fix-pack-requirements'
							)}
						/>
					</Label>
					<Select
						className='mt-1'
						onChange={({target: {value}}) => setDxpVersion(value)}
						value={dxpVersion}
					>
						{dxpVersionsList.map(key => (
							<Select.Item key={key} value={key}>
								{DXP_VERSIONS[key].label}
							</Select.Item>
						))}
					</Select>
				</div>

				<div className='fix-pack-button'>
					<ClayLink
						button
						className='button-root more-information-link mt-4'
						displayType='secondary'
						href={DXP_VERSIONS[dxpVersion].url}
						target='_blank'
					>
						<ClayIcon
							className='icon-root mr-2'
							symbol='shortcut'
						/>

						{Liferay.Language.get('download')}
					</ClayLink>
				</div>
			</div>
		</>
	);
};

const TokenInput: FC<ITokenInputProps> = ({token}) => {
	const [tokenCopied, setTokenCopied] = useState(false);
	const copyButtonClassName = getCN('copy-button', {
		'input-success': tokenCopied
	});

	const _inputRef = useRef<any>();
	const selectAll = () => {
		analytics.track('Clicked Copy Token Button - TEST', null, {
			ip: '0'
		});

		_inputRef.current && _inputRef.current.selectAll();
	};

	return (
		<>
			<div className='w-100 ml-6 mt-1 token-info'>
				{Liferay.Language.get('copy-this-token-to-your-dxp-instance')}
			</div>

			<Input.Group>
				<Input.GroupItem position='prepend'>
					<Input
						className={getCN('text-truncate', 'token-input', {
							'input-success': tokenCopied
						})}
						inset='after'
						onChange={noop}
						onClick={selectAll}
						ref={_inputRef}
						value={token}
					/>

					<Input.Inset
						className={copyButtonClassName}
						position='after'
					>
						<CopyButton
							className={copyButtonClassName}
							displayType='unstyled'
							onClick={() => {
								setTokenCopied(true);

								analytics.track(
									'Clicked Copy Token Button - TEST',
									null,
									{ip: '0'}
								);
							}}
							text={token}
						/>
					</Input.Inset>
				</Input.GroupItem>
			</Input.Group>
		</>
	);
};

export default compose<any>(
	withHistory,
	connect(null, {
		fetchDataSource
	})
)(ConnectDXP);
