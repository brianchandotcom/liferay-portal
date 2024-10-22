import * as API from 'shared/api';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {CSVType, MAX_CSV_ENTRIES, useDownloadCSV} from './utils';
import {DownloadReportButton} from './DownloadReportButton';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';
import {useDispatch} from 'react-redux';
import {useParams} from 'react-router-dom';

interface IDownloadStaticCSVReport {
	disabled: boolean;
	type: CSVType;
	typeLang: string;
	segmentId?: string;
}

export const DownloadStaticCSVReport: React.FC<IDownloadStaticCSVReport> = ({
	disabled,
	segmentId,
	type,
	typeLang
}) => {
	const dispatch = useDispatch();
	const generateURL = useDownloadCSV({segmentId, type});
	const {observer, onOpenChange, open} = useModal();
	const {channelId, groupId} = useParams();

	return (
		<>
			<DownloadReportButton
				disabled={disabled}
				onClick={() => onOpenChange(true)}
			/>

			{open && (
				<Modal
					observer={observer}
					onClose={() => onOpenChange(false)}
					onSubmit={async () => {
						onOpenChange(false);

						try {
							const url = generateURL();
							const response = await API.csv.fetchCSV(url);

							if (!response.ok) {
								throw new Error();
							}

							dispatch(
								addAlert({
									alertType: Alert.Types.Default,
									message: sub(
										Liferay.Language.get(
											'the-x-file-is-being-generated-and-your-download-will-start-soon'
										),
										['CSV']
									) as string
								})
							);

							const a = document.createElement('a');

							a.href = url;
							a.click();

							const count = await API.csv.fetchCount({
								channelId,
								groupId,
								type: CSVType.Individual
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
						} catch (e) {
							dispatch(
								addAlert({
									alertType: Alert.Types.Error,
									message: Liferay.Language.get(
										'it-was-not-possible-to-generate-a-csv-file-at-this-moment.-please-try-again-later'
									)
								})
							);
						}
					}}
					typeLang={typeLang}
				/>
			)}
		</>
	);
};

const Modal = ({observer, onClose, onSubmit, typeLang}) => (
	<ClayModal observer={observer}>
		<ClayForm
			onSubmit={event => {
				event.preventDefault();

				onSubmit();
			}}
		>
			<ClayModal.Header>
				{Liferay.Language.get('download-reports')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p>
					{
						sub(
							Liferay.Language.get(
								'the-generated-csv-file-supports-up-to-x-entries-per-export-and-it-will-respect-the-current-ordering-and-search-results.-please-ensure-that-any-desired-changes-have-been-successfully-applied-before-downloading-the-x-list'
							),
							[toLocale(MAX_CSV_ENTRIES), typeLang]
						) as string
					}
				</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							data-testid='cancel'
							displayType='secondary'
							onClick={onClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton data-testid='submit' type='submit'>
							{Liferay.Language.get('download')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayForm>
	</ClayModal>
);
