import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import DatePicker from 'shared/components/date-picker';
import moment from 'moment';
import React, {useState} from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {formatDate} from './utils';
import {MomentDateRange} from '../DateRangeInput';
import {pickBy} from 'lodash';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {removeUriQueryParam, setUriQueryValues} from 'shared/util/router';
import {spritemap} from 'shared/util/constants';
import {useDispatch} from 'react-redux';
import {useHistory} from 'react-router-dom';

interface IDownloadReportModal {
	alertMessage: string;
	descriptionMessage: string;
	disabled?: boolean;
	infoMessage: string;
	observer: any;
	onClose: () => void;
	onSubmit: () => void;
	requiredDateRange?: boolean;
	showDateRange?: boolean;
}

export const DownloadReportModal: React.FC<IDownloadReportModal> = ({
	alertMessage,
	children,
	descriptionMessage,
	disabled = false,
	infoMessage,
	observer,
	onClose,
	onSubmit,
	requiredDateRange = false,
	showDateRange = true
}) => {
	const dispatch = useDispatch();
	const history = useHistory();
	const [openAlert, setOpenAlert] = useState(true);
	const [dateRange, setDateRange] = useState<MomentDateRange>({
		end: null,
		start: null
	});
	const [submitDisabled, setSubmitDisabled] = useState(false);

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

					if (dateRange && dateRange.end && dateRange.start) {
						history.push(
							setUriQueryValues(
								pickBy({
									downloadReport: true,
									rangeEnd: formatDate(dateRange.end),
									rangeKey: RangeKeyTimeRanges.CustomRange,
									rangeStart: formatDate(dateRange.start)
								}),
								removeUriQueryParam(
									window.location.href,
									'rangeEnd',
									'rangeStart'
								)
							)
						);

						const observer = new MutationObserver(() => {
							const loadingElement = document.querySelectorAll(
								'.page-container .loading-animation'
							);

							if (!loadingElement.length) {
								observer.disconnect();

								onSubmit();
							}
						});

						observer.observe(document.body, {
							attributes: true,
							characterData: true,
							childList: true,
							subtree: true
						});
					} else {
						onSubmit();
					}
				}}
			>
				<ClayModal.Header>
					{Liferay.Language.get('download-report')}
				</ClayModal.Header>

				<ClayModal.Body>
					{openAlert && (
						<ClayAlert
							onClose={() => setOpenAlert(false)}
							spritemap={spritemap}
							title={Liferay.Language.get('info')}
						>
							{infoMessage}
						</ClayAlert>
					)}

					<p>{descriptionMessage}</p>

					{showDateRange && (
						<ClayForm.Group>
							<label htmlFor='timeRange'>
								{Liferay.Language.get('date-range-optional')}
							</label>

							<ClayDropDown
								alignmentPosition={Align.BottomLeft}
								menuElementAttrs={{
									style: {maxWidth: 'none', minWidth: 'none'}
								}}
								trigger={
									<ClayInput.Group>
										<ClayInput.GroupItem prepend>
											<ClayInput
												id='timeRange'
												placeholder={`${Liferay.Language.get(
													'yyyy-mm-dd'
												)} - ${Liferay.Language.get(
													'yyyy-mm-dd'
												)}`}
												readOnly
												type='text'
												value={
													dateRange.start &&
													dateRange.end
														? `${formatDate(
																dateRange.start
														  )} - ${formatDate(
																dateRange.end
														  )}`
														: ''
												}
											/>
										</ClayInput.GroupItem>

										<ClayInput.GroupItem append shrink>
											<ClayInput.GroupText>
												<ClayIcon symbol='calendar' />
											</ClayInput.GroupText>
										</ClayInput.GroupItem>
									</ClayInput.Group>
								}
							>
								<DatePicker
									className='p-2'
									date={dateRange}
									displayLabel={false}
									maxDate={moment().subtract(0, 'd')}
									minDate={moment().subtract(1, 'years')}
									onSelect={({
										end,
										start
									}: MomentDateRange) => {
										setDateRange({
											end,
											start
										});
									}}
								/>
							</ClayDropDown>
						</ClayForm.Group>
					)}

					{children}
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

							<ClayButton
								disabled={
									(requiredDateRange &&
										!Object.keys(dateRange).length) ||
									disabled ||
									submitDisabled
								}
								type='submit'
							>
								{Liferay.Language.get('download')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
};
