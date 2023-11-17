import React from 'react';
import {DownloadReportButton} from './DownloadReportButton';
import {DownloadReportModal, ReportType} from './DownloadReportModal';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';
import {useDownloadCSV} from './utils';
import {useModal} from '@clayui/modal';

export interface IDownloadReport {
	assetId?: string;
	assetType?: string;
	disabled: boolean;
	infoMessage: string;
	type: string;
}

const DownloadCSVReport: React.FC<IDownloadReport> = ({
	assetId,
	assetType,
	disabled,
	infoMessage,
	type
}) => {
	const {onClick} = useDownloadCSV({assetId, assetType, type});
	const {observer, onOpenChange, open} = useModal();

	return (
		<div className='download-report'>
			<DownloadReportButton
				disabled={disabled}
				onClick={() => onOpenChange(true)}
			/>

			{open && (
				<DownloadReportModal
					alertMessage={
						sub(
							Liferay.Language.get(
								'the-x-file-is-being-generated-and-your-download-will-start-soon'
							),
							['CSV']
						) as string
					}
					descriptionMessage={
						sub(
							Liferay.Language.get(
								'select-a-date-range-to-export-this-list-as-a-csv-document.-the-maximum-number-of-entries-supported-per-export-is-x.-the-request-may-take-a-couple-minutes-to-process'
							),
							[toLocale(10000)]
						) as string
					}
					infoMessage={infoMessage}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onSubmit={onClick}
					requiredDateRange
					type={ReportType.CSV}
				/>
			)}
		</div>
	);
};

export default DownloadCSVReport;
