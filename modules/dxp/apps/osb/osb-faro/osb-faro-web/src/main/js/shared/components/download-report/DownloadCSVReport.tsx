import React from 'react';
import {CSVType, useDownloadCSV} from './utils';
import {DownloadReportButton} from './DownloadReportButton';
import {DownloadReportModal, ReportType} from './DownloadReportModal';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';
import {useModal} from '@clayui/modal';
import {useUnsafeQueryRangeSelectors} from 'shared/hooks/useQueryRangeSelectors';

export interface IDownloadReport {
	assetId?: string;
	assetType?: string;
	disabled: boolean;
	type: CSVType;
	typeLang: string;
}

const DownloadCSVReport: React.FC<IDownloadReport> = ({
	assetId,
	assetType,
	disabled,
	type,
	typeLang
}) => {
	const generateURL = useDownloadCSV({assetId, assetType, type});
	const {observer, onOpenChange, open} = useModal();
	const rangeSelectors = useUnsafeQueryRangeSelectors();

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
					infoMessage={
						sub(
							Liferay.Language.get(
								'the-generated-CSV-file-will-respect-the-current-filter-and-search-results,-with-a-maximum-of-x-entries-supported-per-export.-please-ensure-that-any-desired-changes-have-been-successfully-applied-before-downloading-the-individual-x-list'
							),
							[toLocale(10000), typeLang]
						) as string
					}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onSubmit={rangeSelectors => {
						const url = generateURL(rangeSelectors);
						const a = document.createElement('a');

						a.href = url;
						a.click();
					}}
					rangeSelectors={rangeSelectors}
					type={ReportType.CSV}
				/>
			)}
		</div>
	);
};

export default DownloadCSVReport;
