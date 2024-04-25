import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {CSVType, MAX_CSV_ENTRIES, useDownloadCSV} from './utils';
import {DownloadReportButton} from './DownloadReportButton';
import {fetchCount} from 'shared/api/csv';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';
import {useDispatch} from 'react-redux';
import {useParams} from 'react-router-dom';

interface IDownloadIndividualReportModal {
	disabled: boolean;
}

export const DownloadIndividualReportModal: React.FC<IDownloadIndividualReportModal> = ({
	disabled
}) => {
	const dispatch = useDispatch();
	const generateURL = useDownloadCSV({type: CSVType.Individual});
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
						const url = generateURL(null);

						a.href = url;
						a.click();

						try {
							const count = await fetchCount({
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
						} catch (e) {}
					}}
				/>
			)}
		</>
	);
};

export const Modal = ({observer, onClose, onSubmit}) => (
	<ClayModal observer={observer}>
		<ClayForm
			onSubmit={event => {
				event.preventDefault();

				onSubmit();
			}}
		>
			<ClayModal.Header>
				{Liferay.Language.get('download-report')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p>
					{
						sub(
							Liferay.Language.get(
								'this-list-will-be-downloaded-respecting-the-current-ordering-and-search-results'
							),
							[toLocale(MAX_CSV_ENTRIES)]
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
