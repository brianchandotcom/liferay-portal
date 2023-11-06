import React, {useState} from 'react';
import {DownloadReportButton} from './DownloadReportButton';
import {DownloadReportModal} from './DownloadReportModal';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';
import {useModal} from '@clayui/modal';

export interface IDownloadReport {
	disabled: boolean;
	subtitle: string;
	title: string;
}

const DownloadCSVReport: React.FC<IDownloadReport> = ({disabled}) => {
	const {observer, onOpenChange, open} = useModal();
	const [loading, setLoading] = useState(false);

	return (
		<div className='download-report'>
			<DownloadReportButton
				disabled={disabled}
				loading={loading}
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
						'the-individuals-list-will-be-downloaded-respecting-the-current-ordering,-filter-and-search-results.-please-verify-if-the-desired-changes-are-applied'
					)}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onSubmit={() => {
						setLoading(true);

						// TEMP - Implement CSV request

						setTimeout(() => {
							setLoading(false);
						}, 1000);
					}}
				/>
			)}
		</div>
	);
};

export default DownloadCSVReport;
