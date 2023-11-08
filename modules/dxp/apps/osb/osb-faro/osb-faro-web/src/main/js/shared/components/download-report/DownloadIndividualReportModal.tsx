import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {DownloadReportButton} from './DownloadReportButton';
import {sub} from 'shared/util/lang';
import {useDispatch} from 'react-redux';
import {useDownloadCSV} from './utils';

interface IDownloadIndividualReportModal {
	disabled: boolean;
}

export const DownloadIndividualReportModal: React.FC<IDownloadIndividualReportModal> = ({
	disabled
}) => {
	const dispatch = useDispatch();
	const {onClick} = useDownloadCSV({type: 'individual'});
	const {observer, onClose, onOpenChange, open} = useModal();

	return (
		<>
			<DownloadReportButton
				disabled={disabled}
				onClick={() => onOpenChange(true)}
			/>

			{open && (
				<ClayModal observer={observer}>
					<ClayForm
						onSubmit={event => {
							event.preventDefault();

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

							onClick();
						}}
					>
						<ClayModal.Header>
							{Liferay.Language.get('download-report')}
						</ClayModal.Header>

						<ClayModal.Body>
							<p>
								{Liferay.Language.get(
									'this-list-will-be-downloaded-respecting-the-current-ordering,-filter-and-search-results.-are-you-sure-you-want-to-download-the-csv-file'
								)}
							</p>
						</ClayModal.Body>

						<ClayModal.Footer
							last={
								<ClayButton.Group spaced>
									<ClayButton
										displayType='secondary'
										onClick={onClose}
									>
										{Liferay.Language.get('cancel')}
									</ClayButton>

									<ClayButton type='submit'>
										{Liferay.Language.get('download')}
									</ClayButton>
								</ClayButton.Group>
							}
						/>
					</ClayForm>
				</ClayModal>
			)}
		</>
	);
};
