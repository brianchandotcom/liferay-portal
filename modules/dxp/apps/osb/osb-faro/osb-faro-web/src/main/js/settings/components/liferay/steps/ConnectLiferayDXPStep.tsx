import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import Clipboard from 'clipboard';
import getCN from 'classnames';
import React, {useEffect, useState} from 'react';
import {Alert} from 'shared/types';
import {DataSourceStatuses} from 'shared/util/constants';
import {disconnect, fetchToken} from 'shared/api/data-source';
import {modalTypes} from 'shared/actions/modals';
import {Routes, toRoute} from 'shared/util/router';
import {Text} from '@clayui/core';
import {updateSearchParams} from 'settings/components/base-page/utis';
import {useHistory} from 'react-router-dom';
import {useQueryParams} from 'shared/hooks/useQueryParams';
import {useWizardPage} from '../../base-page/WizardPageContext';
import {WizardPageButtonGroup} from 'settings/components/base-page/WizardPageButtonGroup';

const ConnectLiferayDXPStep = ({addAlert, close, groupId, onNext, open}) => {
	const {dataSource, refetchDataSource} = useWizardPage();
	const {dataSourceId} = useQueryParams();
	const history = useHistory();

	const [token, setToken] = useState('');

	useEffect(() => {
		const fetchTokenFn = async () => {
			try {
				const token = await fetchToken(groupId, dataSourceId);

				setToken(token);
			} catch (error) {
				addAlert({
					alertType: Alert.Types.Error,
					message: error.message,
					timeout: false
				});
			}
		};

		fetchTokenFn();
	}, [groupId]);

	if (
		!dataSource ||
		dataSource.get('status', null) !== DataSourceStatuses.Active
	) {
		return (
			<ClayForm
				onSubmit={async event => {
					event.preventDefault();

					try {
						const nextToken = await fetchToken(groupId);

						if (token === nextToken) {
							addAlert({
								alertType: Alert.Types.Error,
								message: Liferay.Language.get(
									'first-paste-the-token-into-your-dxp-instance-in-order-to-continue-with-the-data-source-setup'
								)
							});
						} else {
							onNext();

							addAlert({
								alertType: Alert.Types.Success,
								message: Liferay.Language.get(
									'token-authenticated-successfully'
								)
							});
						}
					} catch (error) {
						addAlert({
							alertType: Alert.Types.Error,
							message: Liferay.Language.get(
								'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
							)
						});
					}
				}}
			>
				<ConnectLiferayDXPFragment
					addAlert={addAlert}
					disabled={false}
					token={token}
				/>

				<WizardPageButtonGroup
					nextButtonLabel={Liferay.Language.get('continue')}
					onCancel={() => {
						history.push(
							toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
								groupId
							})
						);
					}}
					prevButtonLabel={Liferay.Language.get('cancel')}
				/>
			</ClayForm>
		);
	}

	return (
		<ClayForm
			onSubmit={async event => {
				event.preventDefault();

				onNext();
			}}
		>
			<ClayAlert
				displayType='success'
				title={Liferay.Language.get('success')}
			>
				{Liferay.Language.get('token-authenticated-successfully')}
			</ClayAlert>

			<ConnectLiferayDXPFragment
				addAlert={addAlert}
				disabled
				token={token}
			/>

			<WizardPageButtonGroup
				nextButtonLabel={Liferay.Language.get('continue')}
				onCancel={() => {
					open(modalTypes.CONFIRMATION_MODAL, {
						message: (
							<Text as='p' size={4}>
								{Liferay.Language.get(
									'this-action-will-stop-syncing-data-from-your-dxp-instance-to-this-analytics-cloud-workspace.-the-data-that-was-already-synced-will-remain-available-in-the-properties-the-data-source-was-connected-to.-are-you-sure-you-want-to-continue'
								)}
							</Text>
						),
						modalVariant: 'modal-warning',
						onClose: close,
						onSubmit: async () => {
							try {
								await disconnect({
									groupId,
									id: dataSourceId
								});

								updateSearchParams(history, 'dataSourceId', '');

								refetchDataSource(dataSourceId);

								addAlert({
									alertType: Alert.Types.Success,
									message: Liferay.Language.get(
										'data-source-disconnected'
									)
								});
							} catch (error) {
								addAlert({
									alertType: Alert.Types.Error,
									message: Liferay.Language.get(
										'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists-please-contact-support'
									)
								});
							} finally {
								close();
							}
						},
						submitButtonDisplay: 'warning',
						submitMessage: Liferay.Language.get('disconnect'),
						title: Liferay.Language.get('disconnect-data-source'),
						titleIcon: 'warning-full'
					});
				}}
				prevButtonLabel={Liferay.Language.get('disconnect-data-source')}
			/>
		</ClayForm>
	);
};

const ConnectLiferayDXPFragment = ({addAlert, disabled, token}) => {
	const [isUrlCopied, setIsUrlCopied] = useState(false);
	const [copyTitle, setCopyTitle] = useState(
		Liferay.Language.get('click-to-copy')
	);

	useEffect(() => {
		const _clipboard = new Clipboard('[data-clipboard-text]');

		_clipboard.on('success', event => {
			setCopyTitle(Liferay.Language.get('copied'));

			addAlert({
				alertType: Alert.Types.Success,
				message: Liferay.Language.get(
					'copied-successfully-to-the-clipboard'
				)
			});

			setTimeout(() => {
				setCopyTitle(Liferay.Language.get('click-to-copy'));
				setIsUrlCopied(false);
			}, 3000);

			event.clearSelection();
		});

		return () => _clipboard.destroy();
	}, []);

	return (
		<ClayForm.Group
			className={getCN({
				'has-success': isUrlCopied
			})}
		>
			<label htmlFor='token'>
				<Text weight='semi-bold'>
					{Liferay.Language.get(
						'copy-this-token-to-your-dxp-instance'
					)}
				</Text>
			</label>

			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						disabled={disabled}
						id='token'
						insetAfter
						name='token'
						readOnly={!isUrlCopied}
						type='text'
						value={token}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayButton
						aria-label={copyTitle}
						data-clipboard-text={token}
						disabled={disabled}
						displayType={isUrlCopied ? 'success' : 'secondary'}
						onClick={() => setIsUrlCopied(true)}
						outline
						title={copyTitle}
					>
						<ClayIcon symbol={isUrlCopied ? 'check' : 'copy'} />
					</ClayButton>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
};

export {ConnectLiferayDXPStep};
