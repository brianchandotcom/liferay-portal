import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import DatePicker from 'shared/components/date-picker';
import getCN from 'classnames';
import React, {useState} from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {DatePickerRetentionPeriodHeader} from '../DatePickerRetentionPeriodHeader';
import {formatDate} from './utils';
import {formatDateWithTimezone} from '../dropdown-range-key/utils';
import {Moment} from 'moment';
import {MomentDateRange} from '../DateRangeInput';
import {pickBy} from 'lodash';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {removeUriQueryParam, setUriQueryValues} from 'shared/util/router';
import {spritemap} from 'shared/util/constants';
import {useDispatch} from 'react-redux';
import {useHistory} from 'react-router-dom';
import {useRetentionPeriod} from 'shared/hooks/useRetentionPeriod';
import {useTimeZoneId} from 'shared/hooks';

export enum ReportType {
	CSV = 'CSV',
	PDF = 'PDF'
}

interface IDownloadReportModal {
	alertMessage: string;
	date?: MomentDateRange;
	descriptionMessage: string;
	disabled?: boolean;
	infoMessage: string;
	observer: any;
	onClose: () => void;
	onSubmit: (dateRange?: MomentDateRange) => void;
	requiredDateRange?: boolean;
	showDateRange?: boolean;
	type?: ReportType;
	maxDate?: Moment;
	minDate?: Moment;
}

export const DownloadReportModal: React.FC<IDownloadReportModal> = ({
	alertMessage,
	children,
	date = {
		end: null,
		start: null
	},
	descriptionMessage,
	disabled = false,
	infoMessage,
	maxDate: initialMaxDate,
	minDate: initialMinDate,
	observer,
	onClose,
	onSubmit,
	requiredDateRange = false,
	showDateRange = true,
	type
}) => {
	const dispatch = useDispatch();
	const history = useHistory();
	const [openAlert, setOpenAlert] = useState(true);
	const [dateRange, setDateRange] = useState<MomentDateRange>(date);
	const [submitDisabled, setSubmitDisabled] = useState(false);

	const retentionPeriod = useRetentionPeriod();
	const timeZoneId = useTimeZoneId();

	const maxDate =
		initialMaxDate ||
		formatDateWithTimezone(timeZoneId).clone().subtract(1, 'days');
	const minDate =
		initialMinDate ||
		formatDateWithTimezone(timeZoneId)
			.clone()
			.subtract(retentionPeriod, 'month');

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
						onSubmit(dateRange);

						return;
					}

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
								{requiredDateRange
									? Liferay.Language.get('date-range')
									: Liferay.Language.get(
											'date-range-optional'
									  )}
							</label>

							<ClayDropDown
								alignmentPosition={Align.BottomLeft}
								menuElementAttrs={{
									className: getCN(
										'dropdown-range-key-menu-root',
										{'show-date-picker': showDateRange}
									)
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
									date={dateRange}
									displayLabel={false}
									header={
										<DatePickerRetentionPeriodHeader
											retentionPeriod={retentionPeriod}
										/>
									}
									maxDate={maxDate}
									maxRange={365}
									minDate={minDate}
									onSelect={({end, start}) => {
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
								data-testid='cancel'
								displayType='secondary'
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								data-testid='submit'
								disabled={
									(requiredDateRange &&
										!dateRange.end &&
										!dateRange.start) ||
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
