import * as API from 'shared/api';
import ClayButton from '@clayui/button';
import getCN from 'classnames';
import Icon from 'shared/components/Icon';
import React, {useEffect, useState} from 'react';
import Spinner from 'shared/components/Spinner';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {close, modalTypes, open} from 'shared/actions/modals';
import {connect, ConnectedProps} from 'react-redux';
import {sub} from 'shared/util/lang';
import {useParams} from 'react-router-dom';

export enum Frequency {
	Daily = 'daily',
	Monthly = 'monthly',
	Weekly = 'weekly'
}

export type Report = {
	enabled: boolean;
	frequency: Frequency.Daily | Frequency.Monthly | Frequency.Weekly;
};

interface IEmailReportsProps
	extends React.HTMLAttributes<HTMLElement>,
		PropsFromRedux {
	channelId: string;
	sitesSynced?: boolean;
}

const connector = connect(null, {
	addAlert,
	close,
	open
});

type PropsFromRedux = ConnectedProps<typeof connector>;

const EmailReports: React.FC<IEmailReportsProps> = ({
	addAlert,
	channelId,
	className,
	close,
	open,
	sitesSynced = false
}) => {
	const {groupId} = useParams();
	const [report, setReport] = useState<Report | null>(null);

	useEffect(() => {
		API.preferences
			.fetchEmailReport({groupId})
			.then((reports: {[key: string]: Report}) =>
				setReport(
					reports?.[channelId] ?? {
						enabled: false,
						frequency: Frequency.Monthly
					}
				)
			)
			.catch(e => {
				console.error(e); // eslint-disable-line no-console
			});
	}, [channelId, groupId]);

	const handleSaveReport = (report: Report): void => {
		API.preferences
			.updateEmailReport({channelId, groupId, report})
			.then(() => {
				close();

				addAlert({
					alertType: Alert.Types.Success,
					message: Liferay.Language.get(
						'changes-to-email-reports-saved'
					)
				});

				setReport(report);
			})
			.catch(e => {
				console.error(e); // eslint-disable-line no-console

				addAlert({
					alertType: Alert.Types.Error,
					message: Liferay.Language.get('error'),
					timeout: false
				});
			});
	};

	return (
		<span className={getCN('font-weight-semibold mr-2', className)}>
			{sub(
				Liferay.Language.get('email-reports-x'),
				[
					!report ? (
						<Spinner className='ml-2' key='LOADING' size='sm' />
					) : report.enabled ? (
						Liferay.Language.get('enabled')
					) : (
						Liferay.Language.get('disabled')
					)
				],
				false
			)}

			{report && (
				<ClayButton
					borderless
					className='button-root'
					disabled={!sitesSynced}
					displayType='unstyled'
					onClick={() => {
						if (!sitesSynced) {
							return;
						}

						open(modalTypes.EDIT_EMAIL_REPORTS, {
							onCancel: close,
							onSave: handleSaveReport,
							report
						});
					}}
					size='sm'
				>
					<span
						className='ml-2 p-2'
						data-tooltip
						data-tooltip-align='top'
						title={Liferay.Language.get('configure-email-reports')}
					>
						<Icon symbol='cog' />
					</span>
				</ClayButton>
			)}
		</span>
	);
};

export default connector(EmailReports);
