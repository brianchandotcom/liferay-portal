import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import React, {useState} from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert, RangeSelectors} from 'shared/types';
import {Align} from '@clayui/drop-down';
import {DropdownRangeKey} from '../dropdown-range-key/DropdownRangeKey';
import {pickBy} from 'lodash';
import {setUriQueryValues} from 'shared/util/router';
import {spritemap} from 'shared/util/constants';
import {Text} from '@clayui/core';
import {useDispatch} from 'react-redux';
import {useHistory} from 'react-router-dom';
import {useLoadingObserver} from 'shared/hooks/useLoadingObserver';

export enum ReportType {
	CSV = 'CSV',
	PDF = 'PDF'
}

interface IDownloadReportModal {
	alertMessage: string;
	disabled?: boolean;
	infoMessage: string;
	observer: any;
	onClose: () => void;
	onSubmit: (rangeSelectors?: RangeSelectors) => void;
	rangeSelectors?: RangeSelectors;
	requiredDateRange?: boolean;
	showDateRange?: boolean;
	type?: ReportType;
}

export const DownloadReportModal: React.FC<IDownloadReportModal> = ({
	alertMessage,
	children,
	disabled = false,
	infoMessage,
	observer,
	onClose,
	onSubmit,
	rangeSelectors: initialRangeSelectors,
	requiredDateRange = false,
	showDateRange = true,
	type
}) => {
	const dispatch = useDispatch();
	const history = useHistory();
	const [openAlert, setOpenAlert] = useState(true);
	const [submitDisabled, setSubmitDisabled] = useState(false);
	const [rangeSelectors, setRangeSelectors] = useState<RangeSelectors>(
		initialRangeSelectors
	);

	const loadingObserver = useLoadingObserver();

	return (
		<ClayModal observer={observer}>
			<ClayForm
				onSubmit={event => {
					event.preventDefault();

					setSubmitDisabled(true);

					onClose();

					dispatch(
						addAlert({
							alertType: Alert.Types.Default,
							message: alertMessage
						})
					);

					if (type === 'CSV') {
						onSubmit(rangeSelectors);

						return;
					}

					if (rangeSelectors) {
						history.push(
							setUriQueryValues(
								pickBy({
									downloadReport: true,
									...rangeSelectors
								})
							)
						);

						if (!loadingObserver) {
							onSubmit();
						}
					} else {
						onSubmit();
					}
				}}
			>
				<div className='modal-content'>
					<ClayModal.Header>
						{Liferay.Language.get('download-report')}
					</ClayModal.Header>

					{openAlert && (
						<ClayAlert
							onClose={() => setOpenAlert(false)}
							spritemap={spritemap}
							title={Liferay.Language.get('info')}
							variant='stripe'
						>
							{infoMessage}
						</ClayAlert>
					)}

					<ClayModal.Body>
						{showDateRange && (
							<ClayForm.Group className='mb-0'>
								<label htmlFor='timeRange'>
									{requiredDateRange
										? Liferay.Language.get('date-range')
										: Liferay.Language.get(
												'date-range-optional'
										  )}
								</label>

								<p>
									<Text size={3}>
										{Liferay.Language.get(
											'only-select-a-date-range-if-you-want-to-modify-the-current-date-filter'
										)}
									</Text>
								</p>

								<DropdownRangeKey
									alignmentPosition={Align.BottomLeft}
									legacy={false}
									onRangeSelectorChange={setRangeSelectors}
									rangeSelectors={rangeSelectors}
								/>
							</ClayForm.Group>
						)}

						{children}
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

								<ClayButton
									data-testid='submit'
									disabled={disabled || submitDisabled}
									type='submit'
								>
									{Liferay.Language.get('download')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</div>
			</ClayForm>
		</ClayModal>
	);
};
