import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {CSVType, formatDate, MAX_CSV_ENTRIES, useDownloadCSV} from './utils';
import {DownloadReportButton} from './DownloadReportButton';
import {DownloadReportModal, ReportType} from './DownloadReportModal';
import {fetchCount} from 'shared/api/csv';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';
import {useDispatch} from 'react-redux';
import {useModal} from '@clayui/modal';
import {useParams} from 'react-router-dom';
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
	const dispatch = useDispatch();
	const generateURL = useDownloadCSV({assetId, assetType, type});
	const {observer, onOpenChange, open} = useModal();
	const rangeSelectors = useUnsafeQueryRangeSelectors();
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
					infoMessage={
						sub(
							Liferay.Language.get(
								'the-generated-CSV-file-will-respect-the-current-filter-and-search-results,-with-a-maximum-of-x-entries-supported-per-export.-please-ensure-that-any-desired-changes-have-been-successfully-applied-before-downloading-the-individual-x-list'
							),
							[toLocale(MAX_CSV_ENTRIES), typeLang]
						) as string
					}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onSubmit={async rangeSelectors => {
						const url = generateURL(rangeSelectors);
						const a = document.createElement('a');

						a.href = url;
						a.click();

						let rangeKey;
						let fromDate;
						let toDate;

						if (rangeSelectors) {
							if (
								rangeSelectors.rangeKey ===
								RangeKeyTimeRanges.CustomRange
							) {
								(rangeKey = 'CUSTOM'),
									(fromDate = formatDate(
										rangeSelectors?.rangeStart
									));
								toDate = formatDate(rangeSelectors?.rangeEnd);
							} else {
								rangeKey = rangeSelectors.rangeKey;
							}
						}

						try {
							const count = await fetchCount({
								assetId,
								assetType,
								channelId,
								fromDate,
								groupId,
								rangeKey,
								toDate,
								type
							});

							if (count > MAX_CSV_ENTRIES) {
								dispatch(
									addAlert({
										alertType: Alert.Types.Warning,
										message: sub(
											Liferay.Language.get(
												'the-csv-file-reached-x-entries'
											),
											[toLocale(MAX_CSV_ENTRIES)]
										)
									})
								);
							}
						} catch (e) {}
					}}
					rangeSelectors={rangeSelectors}
					type={ReportType.CSV}
				/>
			)}
		</div>
	);
};

export default DownloadCSVReport;
