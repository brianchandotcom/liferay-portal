/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';
import {Redirect} from 'react-router-dom';
import MaskedInput from 'react-text-mask';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';

import Icon from '../../shared/components/Icon.es';
import MultiSelect from '../../shared/components/MultiSelect.es';
import LoadingState from '../../shared/components/loading/LoadingState.es';
import {
	BackLink,
	BackRedirect
} from '../../shared/components/router/routerWrapper.es';
import {openErrorToast} from '../../shared/util/toast.es';
import {AppContext, AppStatus} from '../AppContext.es';
import {
	ALERT_MESSAGE,
	CALENDAR_KEY,
	DAYS,
	DURATION,
	HOURS,
	NAME,
	PAUSE_NODE_KEYS,
	START_NODE_KEYS,
	STOP_NODE_KEYS
} from './Constants.es';
import FieldError from './form/fieldError.es';
import FieldLabel from './form/fieldLabel.es';
import {Errors, useErrors} from './store/ErrorsStore.es';
import {SLANodes, useSLANodes} from './store/SLANodeStore.es';
import {SLA, useSLA} from './store/SLAStore.es';
import calendarStore from './store/calendarStore.es';
import {
	hasErrors,
	validateDuration,
	validateHours,
	validateName,
	validateNodeKeys
} from './util/slaFormUtil.es';

const {useContext, useState} = React;

/**
 * SLA form component.
 * @extends React.Component
 */
function SLAForm({id, processId, query}) {
	const {client} = useContext(AppContext);
	const errorsState = useErrors();

	return (
		<div className="sla-form">
			<Errors.Provider value={errorsState}>
				<SLANodes.Provider value={useSLANodes(processId, client)}>
					<SLA.Provider
						value={useSLA(client, id, processId, errorsState)}
					>
						<SLAForm.Body
							id={id}
							processId={processId}
							query={query}
						/>
					</SLA.Provider>
				</SLANodes.Provider>
			</Errors.Provider>
		</div>
	);
}

const AlertMessage = () => {
	const {errors} = useContext(Errors);

	return (
		<div className="alert-container">
			<div className="alert alert-danger" role="alert">
				<span className="alert-indicator">
					<Icon iconName="exclamation-full" />
				</span>

				<strong className="lead">
					{Liferay.Language.get('error')}
				</strong>

				<span>{errors[ALERT_MESSAGE]}</span>
			</div>
		</div>
	);
};

const AlertWorkflowDefinitionChange = () => {
	return (
		<div className="alert-container">
			<div className="alert alert-danger alert-dismissible" role="alert">
				<span className="alert-indicator">
					<Icon iconName="exclamation-full" />
				</span>

				<strong className="lead">
					{Liferay.Language.get('error')}
				</strong>

				{Liferay.Language.get(
					'the-time-frame-options-changed-in-the-workflow-definition'
				)}

				<button
					aria-label="Close"
					className="close"
					data-dismiss="alert"
					type="button"
				>
					<Icon iconName="times" />
				</button>
			</div>
		</div>
	);
};

const Body = ({id, processId, query}) => {
	const {calendars} = calendarStore.getState();
	const {
		changeNodesKeys,
		changePauseNodes,
		changeValue,
		filterNodeTagIds,
		pauseNodeTagIds,
		sla: {
			calendarKey = calendarStore.defaultCalendar.key,
			days,
			description,
			hours,
			name,
			pauseNodeKeys: {nodeKeys: pauseNodeKeys},
			startNodeKeys: {nodeKeys: startNodeKeys},
			status,
			stopNodeKeys: {nodeKeys: stopNodeKeys}
		}
	} = useContext(SLA);
	const {errors, setErrors} = useContext(Errors);
	const {getPauseNodes, getStartNodes, getStopNodes, nodes} = useContext(
		SLANodes
	);
	const [loading, setLoading] = useState(false);

	const showCalendarField = calendars.length > 1;

	const daysMask = createNumberMask({
		allowLeadingZeroes: true,
		includeThousandsSeparator: false,
		integerLimit: 4,
		prefix: ''
	});

	const checkNodeErrors = type => filteredNodeKeys => {
		if (type !== PAUSE_NODE_KEYS) {
			errors[type] = validateNodeKeys(filteredNodeKeys);
		}
		errors[ALERT_MESSAGE] = '';
		setErrors({...errors});
	};

	const hideDropLists = () => () => {
		document.dispatchEvent(new Event('mousedown', {bubbles: true}));
	};

	const onChangeHandler = (saveFunc, validationFunc) => ({
		target: {name, value = ''}
	}) => {
		saveFunc(name, value);
		if (typeof validationFunc === 'function') {
			validationFunc(value);
		}
	};

	const onDurationChanged = (oldDays, oldHours) => days => {
		errors[ALERT_MESSAGE] = '';
		errors[DURATION] = validateDuration(days, oldHours);
		errors[HOURS] = '';

		setErrors({...errors});
	};

	const onHoursBlurred = (days, hours) => () => {
		if (days && Number(days) > 0 && (!hours || hours === '00:00')) {
			errors[ALERT_MESSAGE] = '';
			errors[HOURS] = '';
			setErrors({...errors});
		}
		else {
			const beforeHoursError = errors[HOURS];

			errors[ALERT_MESSAGE] = '';
			errors[HOURS] = validateHours(hours);

			if (hours && hours === '00:00') {
				errors[HOURS] = Liferay.Language.get(
					'value-must-be-an-hour-below'
				);
			}

			if (beforeHoursError !== errors[HOURS]) {
				setErrors({...errors});
			}
		}
	};

	const onNameChanged = name => {
		errors[ALERT_MESSAGE] = '';
		errors[NAME] = validateName(name);
		setErrors({...errors});
	};

	const onReloadNodesHandler = () => on => {
		setLoading(on);
	};

	let calendarString = Liferay.Language.get(
		'define-the-sla-duration-and-calendar-format'
	);

	if (!showCalendarField) {
		calendarString = Liferay.Language.get('define-the-sla-duration');
	}

	if (loading) {
		return <LoadingState className="" />;
	}

	return (
		<>
			{errors[ALERT_MESSAGE] && <SLAForm.AlertMessage />}

			{status === 2 && <SLAForm.AlertWorkflowDefinitionChange />}

			<form className="sheet sheet-lg" role="form">
				<div className="sheet-header">
					<h2 className="sheet-title">
						{Liferay.Language.get('sla-definition')}
					</h2>
				</div>

				<div className="sheet-section">
					<div className="row">
						<div
							className={`col col-sm-5 form-group ${
								errors[NAME] ? 'has-error' : ''
							}`}
						>
							<FieldLabel
								fieldId="sla_name"
								required
								text={Liferay.Language.get('name')}
							/>

							<input
								autoFocus
								className="form-control"
								defaultValue={name}
								id="sla_name"
								maxLength={75}
								name="name"
								onInput={onChangeHandler(
									changeValue,
									onNameChanged
								)}
								type="text"
							/>

							{errors[NAME] && (
								<FieldError error={errors[NAME]} />
							)}
						</div>

						<div className="col col-sm-7 form-group">
							<FieldLabel
								fieldId="sla_description"
								text={Liferay.Language.get('description')}
							/>

							<input
								className="form-control"
								defaultValue={description}
								id="sla_description"
								name="description"
								onFocus={hideDropLists()}
								onInput={onChangeHandler(changeValue)}
								type="text"
							/>
						</div>
					</div>

					<h3 className="sheet-subtitle">
						<FieldLabel
							fieldId="sla_time_start"
							text={Liferay.Language.get(
								'time-frame'
							).toUpperCase()}
						/>
					</h3>

					<div className="sheet-text">
						{Liferay.Language.get(
							'define-when-time-should-be-tracked-based-on-workflow-steps'
						)}
					</div>

					<div className="row">
						<div
							className={`col col-sm-12 form-group ${
								errors[START_NODE_KEYS] ? 'has-error' : ''
							}`}
						>
							<FieldLabel
								fieldId="sla_time_start"
								required
								text={Liferay.Language.get('start')}
							/>

							<div className="form-text">
								{Liferay.Language.get(
									'time-will-begin-counting-when'
								)}
							</div>

							<MultiSelect
								data={getStartNodes(
									pauseNodeKeys,
									stopNodeKeys
								)}
								fieldId="compositeId"
								onChangeTags={changeNodesKeys(
									START_NODE_KEYS,
									nodes,
									checkNodeErrors(START_NODE_KEYS)
								)}
								selectedTagsId={filterNodeTagIds(
									getStartNodes(pauseNodeKeys, stopNodeKeys),
									startNodeKeys
								)}
							/>

							{errors[START_NODE_KEYS] && (
								<FieldError error={errors[START_NODE_KEYS]} />
							)}
						</div>
					</div>

					<div className="row">
						<div className="col col-sm-12 form-group">
							<FieldLabel
								fieldId="sla_time_pause"
								text={Liferay.Language.get('pause')}
							/>

							<div className="form-text">
								{Liferay.Language.get(
									'time-wont-be-considered-when'
								)}
							</div>

							<MultiSelect
								data={getPauseNodes(
									startNodeKeys,
									stopNodeKeys
								)}
								fieldId="compositeId"
								onChangeTags={changePauseNodes(
									getPauseNodes(startNodeKeys, stopNodeKeys),
									checkNodeErrors(PAUSE_NODE_KEYS)
								)}
								selectedTagsId={pauseNodeTagIds(
									getPauseNodes(startNodeKeys, stopNodeKeys),
									pauseNodeKeys
								)}
							/>
						</div>
					</div>

					<div className="row">
						<div
							className={`col col-sm-12 form-group ${
								errors[STOP_NODE_KEYS] ? 'has-error' : ''
							}`}
						>
							<FieldLabel
								fieldId="sla_time_stop"
								required
								text={Liferay.Language.get('stop')}
							/>

							<div className="form-text">
								{Liferay.Language.get(
									'time-will-stop-counting-when'
								)}
							</div>

							<MultiSelect
								data={getStopNodes(
									pauseNodeKeys,
									startNodeKeys
								)}
								fieldId="compositeId"
								onChangeTags={changeNodesKeys(
									STOP_NODE_KEYS,
									nodes,
									checkNodeErrors(STOP_NODE_KEYS)
								)}
								selectedTagsId={filterNodeTagIds(
									getStopNodes(pauseNodeKeys, startNodeKeys),
									stopNodeKeys
								)}
							/>

							{errors[STOP_NODE_KEYS] && (
								<FieldError error={errors[STOP_NODE_KEYS]} />
							)}
						</div>
					</div>

					<h3 className="sheet-subtitle">
						<FieldLabel
							required
							text={Liferay.Language.get(
								'duration'
							).toUpperCase()}
						/>
					</h3>

					<div className="sheet-text">{calendarString}</div>

					<div className="row">
						<div
							className={`col col-sm-3 form-group ${
								errors[DURATION] ? 'has-error' : ''
							}`}
						>
							<FieldLabel
								fieldId="sla_duration_days"
								text={Liferay.Language.get('days')}
							/>

							<MaskedInput
								className="form-control"
								defaultValue={days}
								id="sla_duration_days"
								mask={daysMask}
								maxLength={4}
								name={DAYS}
								onFocus={hideDropLists()}
								onInput={onChangeHandler(
									changeValue,
									onDurationChanged(days, hours)
								)}
							/>

							{errors[DURATION] && (
								<FieldError error={errors[DURATION]} />
							)}

							<div className="form-text">
								{Liferay.Language.get('enter-a-whole-number')}
							</div>
						</div>

						<div
							className={`col col-sm-3 form-group ${
								errors[DURATION] || errors[HOURS]
									? 'has-error'
									: ''
							}`}
						>
							<FieldLabel
								fieldId="sla_duration_hours"
								text={Liferay.Language.get('hours')}
							/>

							<MaskedInput
								className="form-control"
								id="sla_duration_hours"
								mask={[/\d/, /\d/, ':', /\d/, /\d/]}
								name={HOURS}
								onBlur={onHoursBlurred(days, hours)}
								onChange={onChangeHandler(
									changeValue,
									onDurationChanged(days, hours)
								)}
								onInput={onChangeHandler(changeValue)}
								placeholder="00:00"
								value={hours}
							/>

							{errors[DURATION] && (
								<FieldError error={errors[DURATION]} />
							)}

							{errors[HOURS] && (
								<FieldError error={errors[HOURS]} />
							)}
						</div>

						{showCalendarField && (
							<div className="col col-sm-6 form-group">
								<FieldLabel
									fieldId="sla_calendar_key"
									text={Liferay.Language.get('calendar')}
								/>

								<select
									className="form-control"
									id="sla_calendar_key"
									name={CALENDAR_KEY}
									onChange={onChangeHandler(changeValue)}
									value={calendarKey}
								>
									{calendars.map((calendar, index) => (
										<option
											key={index}
											value={calendar.key}
										>
											{calendar.title}

											{calendar.defaultCalendar &&
												` (${Liferay.Language.get(
													'system-default'
												)})`}
										</option>
									))}
								</select>
							</div>
						)}
					</div>
				</div>

				<SLAForm.Footer
					id={id}
					onReloadNodes={onReloadNodesHandler()}
					processId={processId}
					query={query}
				/>
			</form>
		</>
	);
};

const Footer = ({id, onReloadNodes, processId, query}) => {
	const {defaultDelta, setStatus} = useContext(AppContext);
	const {errors, setErrors} = useContext(Errors);
	const {fetchNodes} = useContext(SLANodes);
	const [redirectToSLAList, setRedirectToSLAList] = useState(false);
	const {resetNodes, saveSLA, sla} = useContext(SLA);

	const handleErrors = responseErrors => {
		if (Array.isArray(responseErrors)) {
			responseErrors.forEach(error => {
				const errorKey = error.fieldName || ALERT_MESSAGE;

				errors[errorKey] = error.message;
			});

			const fieldNames = responseErrors.map(error => error.fieldName);

			if (
				fieldNames.includes(PAUSE_NODE_KEYS) ||
				fieldNames.includes(START_NODE_KEYS) ||
				fieldNames.includes(STOP_NODE_KEYS)
			) {
				resetNodes();
				onReloadNodes(true);
				fetchNodes(processId).then(() => onReloadNodes(false));
				setErrors({...errors});
			}
			else {
				setErrors({...errors});
			}
		}
		else {
			openErrorToast({
				message: Liferay.Language.get('your-request-has-failed')
			});
		}
	};

	const handleSubmit = (
		{days, hours, id, name, processId, startNodeKeys, stopNodeKeys},
		saveCallback
	) => () => {
		errors[ALERT_MESSAGE] = '';
		errors[DURATION] = validateDuration(days, hours);
		errors[HOURS] = validateHours(hours);
		errors[NAME] = validateName(name);
		errors[PAUSE_NODE_KEYS] = '';
		errors[START_NODE_KEYS] = validateNodeKeys(startNodeKeys.nodeKeys);
		errors[STOP_NODE_KEYS] = validateNodeKeys(stopNodeKeys.nodeKeys);

		if ((!hours || hours === '00:00') && days && Number(days) > 0) {
			errors[HOURS] = '';
		}

		if (hasErrors(errors)) {
			errors[ALERT_MESSAGE] = Liferay.Language.get(
				'please-fill-in-the-required-fields'
			);
			setErrors({...errors});
		}
		else {
			saveCallback(processId, id, calendarStore.defaultCalendar.key)
				.then(() => {
					const status = id
						? AppStatus.slaUpdated
						: AppStatus.slaSaved;

					setStatus(status);
					setRedirectToSLAList(true);
				})
				.catch(error => {
					const {data} = error.response || {};

					handleErrors(data);
				});
		}
	};

	return (
		<>
			{redirectToSLAList && id ? (
				<BackRedirect />
			) : redirectToSLAList ? (
				<Redirect
					to={{
						pathname: `/slas/${processId}/${defaultDelta}/1`,
						search: query
					}}
				/>
			) : null}
			<div className="sheet-footer sheet-footer-btn-block-sm-down">
				<div className="btn-group">
					<div className="btn-group-item">
						<button
							className="btn btn-primary"
							onClick={handleSubmit(
								{...sla, ...{id, processId}},
								saveSLA
							)}
							type="button"
						>
							{id
								? Liferay.Language.get('update')
								: Liferay.Language.get('save')}
						</button>
					</div>

					<div className="btn-group-item">
						<BackLink className="btn btn-secondary">
							{Liferay.Language.get('cancel')}
						</BackLink>
					</div>
				</div>
			</div>
		</>
	);
};

SLAForm.AlertMessage = AlertMessage;
SLAForm.AlertWorkflowDefinitionChange = AlertWorkflowDefinitionChange;
SLAForm.Body = Body;
SLAForm.Footer = Footer;
export default SLAForm;
