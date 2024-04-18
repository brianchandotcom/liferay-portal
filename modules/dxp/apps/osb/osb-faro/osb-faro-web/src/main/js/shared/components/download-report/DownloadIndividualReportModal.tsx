import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {CSVType, useDownloadCSV} from './utils';
import {DownloadReportButton} from './DownloadReportButton';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';
import {useDispatch} from 'react-redux';

interface IDownloadIndividualReportModal {
	disabled: boolean;
}

export const DownloadIndividualReportModal: React.FC<IDownloadIndividualReportModal> = ({
	disabled
}) => {
	const dispatch = useDispatch();
	const generateURL = useDownloadCSV({type: CSVType.Individual});
	const {observer, onOpenChange, open} = useModal();

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
					onSubmit={() => {
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
							[toLocale(10000)]
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
