import React from 'react';
import {createOrderByField} from 'shared/util/pagination';
import {DownloadReportButton} from './DownloadReportButton';
import {DownloadReportModal} from './DownloadReportModal';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';
import {useModal} from '@clayui/modal';
import {useParams} from 'react-router-dom';

export interface IDownloadReport {
	assetId?: string;
	assetType?: string;
	disabled: boolean;
	type: string;
}

const DownloadCSVReport: React.FC<IDownloadReport> = ({
	assetId,
	assetType,
	disabled,
	type
}) => {
	const {observer, onOpenChange, open} = useModal();
	const {channelId, groupId} = useParams();

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
					infoMessage={Liferay.Language.get(
						'the-individuals-list-will-be-downloaded-respecting-the-current-ordering,-filter,-and-search-results.-please-verify-if-the-desired-changes-are-applied'
					)}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onSubmit={() => {
						const searchParams = new URLSearchParams(
							location.search
						);

						const field = searchParams.get('field');
						const query = searchParams.get('query');
						const rangeEnd = searchParams.get('rangeEnd');
						const rangeStart = searchParams.get('rangeStart');
						const sortOrder = searchParams.get('sortOrder');

						const a = document.createElement('a');

						let url = `/o/faro/main/${groupId}/reports/export/csv/${type}?channelId=${channelId}&fromDate=${rangeStart}&toDate=${rangeEnd}`;

						if (assetId) {
							url += `&assetId=${encodeURIComponent(assetId)}`;
						}

						if (assetType) {
							url += `&assetType=${assetType}`;
						}

						if (field && sortOrder) {
							url += `&orderByFields=${JSON.stringify(
								createOrderByField(field, sortOrder)
							)}`;
						}

						if (query) {
							url += `&query=${query}`;
						}

						a.href = url;

						a.click();
					}}
					requiredDateRange
				/>
			)}
		</div>
	);
};

export default DownloadCSVReport;
