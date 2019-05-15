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
} from './Constants';
import { AppContext, AppStatus } from '../AppContext';
import {
	BackLink,
	BackRedirect
} from '../../shared/components/router/routerWrapper';
import {
	hasErrors,
	validateDuration,
	validateHours,
	validateName,
	validateNodeKeys
} from './util/slaFormUtil';
import autobind from 'autobind-decorator';
import calendarStore from './store/calendarStore';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import FieldError from './form/fieldError';
import FieldLabel from './form/fieldLabel';
import Icon from '../../shared/components/Icon';
import LoadingState from '../../shared/components/loading/LoadingState';
import MaskedInput from 'react-text-mask';
import MultiSelect from '../../shared/components/MultiSelect';
import nodeStore from './store/nodeStore';
import { openErrorToast } from '../../shared/util/toast';
import React from 'react';
import { Redirect } from 'react-router-dom';
import slaStore from './store/slaStore';

/**
 * SLA form component.
 * @extends React.Component
 */
class SLAForm extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			errors: {},
			loading: false,
			redirectToSLAList: false
		};
	}

	componentWillMount() {
		this.loadData();
	}

	@autobind
	handleChange(
		{
			target: { name, value = '' }
		},
		callback
	) {
		slaStore.setState({ [name]: value });
		this.setState({}, callback);
	}

	handleErrors(responseErrors) {
		if (Array.isArray(responseErrors)) {
			const { errors } = this.state;

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
				this.reloadNodes().then(() => {
					slaStore.resetNodes();

					this.setState({ errors });
				});
			}
			else {
				this.setState({ errors });
			}
		}
		else {
			openErrorToast({
				message: Liferay.Language.get(
					'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
				)
			});
		}
	}

	@autobind
	handlePauseNodesChange(nodeKeys) {
		const { errors } = this.state;
		const { startNodeKeys, stopNodeKeys } = slaStore.getState();

		const pauseNodeKeys = nodeStore
			.getPauseNodes(startNodeKeys.nodeKeys, stopNodeKeys.nodeKeys)
			.filter(({ compositeId }) => nodeKeys.includes(`${compositeId}`));

		slaStore.setState({ pauseNodeKeys: { nodeKeys: pauseNodeKeys } });
		errors[PAUSE_NODE_KEYS] = '';
		this.setState({ errors });
	}

	@autobind
	handleStartNodes(nodeKeys) {
		const { errors } = this.state;
		const startNodeKeys = nodeStore
			.getState()
			.nodes.filter(({ compositeId }) => nodeKeys.includes(`${compositeId}`));

		errors[ALERT_MESSAGE] = '';
		errors[START_NODE_KEYS] = validateNodeKeys(startNodeKeys);
		slaStore.setState({ startNodeKeys: { nodeKeys: startNodeKeys } });
		this.setState({ errors });
	}

	@autobind
	handleStopNodesChange(nodeKeys) {
		const { errors } = this.state;
		const stopNodeKeys = nodeStore
			.getState()
			.nodes.filter(({ compositeId }) => nodeKeys.includes(`${compositeId}`));

		errors[ALERT_MESSAGE] = '';
		errors[STOP_NODE_KEYS] = validateNodeKeys(stopNodeKeys);
		slaStore.setState({ stopNodeKeys: { nodeKeys: stopNodeKeys } });
		this.setState({ errors });
	}

	@autobind
	handleSubmit() {
		const {
			days,
			hours,
			name,
			startNodeKeys,
			stopNodeKeys
		} = slaStore.getState();
		const { errors } = this.state;

		errors[ALERT_MESSAGE] = '';
		errors[DURATION] = validateDuration(days, hours);
		errors[HOURS] = validateHours(hours);
		errors[NAME] = validateName(name);
		errors[PAUSE_NODE_KEYS] = '';
		errors[START_NODE_KEYS] = validateNodeKeys(startNodeKeys.nodeKeys);
		errors[STOP_NODE_KEYS] = validateNodeKeys(stopNodeKeys.nodeKeys);

		if (hasErrors(errors)) {
			errors[ALERT_MESSAGE] = Liferay.Language.get(
				'please-fill-in-the-required-fields'
			);

			this.setState({ errors });
		}
		else {
			const { id, processId } = this.props;

			return slaStore
				.saveSLA(processId, id)
				.then(() => {
					const status = id ? AppStatus.slaUpdated : AppStatus.slaSaved;

					this.context.setStatus(status, () => {
						this.setState({ redirectToSLAList: true });
					});
				})
				.catch(result => {
					const {
						response: { data }
					} = result;

					this.handleErrors(data);
				});
		}
	}

	@autobind
	hideDropLists() {
		document.dispatchEvent(new Event('mousedown', { bubbles: true }));
	}

	loadData() {
		const { id, processId } = this.props;

		slaStore.reset();

		const promises = [
			calendarStore.fetchCalendars(),
			nodeStore.fetchNodes(processId)
		];

		return Promise.all(promises)
			.then(() => (id ? slaStore.fetchData(id) : Promise.resolve()))
			.then(() => this.loadDataCallback(id));
	}

	loadDataCallback(id) {
		const { errors } = this.state;
		const errorText = Liferay.Language.get(
			'selected-option-is-no-longer-available'
		);
		const { pauseNodeKeys, startNodeKeys, stopNodeKeys } = slaStore.getState();

		if (startNodeKeys.status === 2 && !pauseNodeKeys.nodeKeys.length) {
			errors[START_NODE_KEYS] = errorText;
		}

		if (stopNodeKeys.status === 2 && !pauseNodeKeys.nodeKeys.length) {
			errors[STOP_NODE_KEYS] = errorText;
		}

		this.setState({
			errors,
			loading: false
		});

		if (id) {
			this.context.setTitle(slaStore.getState().name);
		}
		else {
			this.context.setTitle(Liferay.Language.get('new-sla'));
		}
	}

	@autobind
	onDurationChanged() {
		const { errors } = this.state;
		const { days, hours } = slaStore.getState();

		errors[ALERT_MESSAGE] = '';
		errors[DURATION] = validateDuration(days, hours);
		errors[HOURS] = '';

		this.setState({ errors });
	}

	@autobind
	onHoursBlurred() {
		const { errors } = this.state;
		const { hours } = slaStore.getState();

		errors[ALERT_MESSAGE] = '';
		errors[HOURS] = validateHours(hours);

		this.setState({ errors });
	}

	@autobind
	onNameChanged() {
		const { errors } = this.state;
		const { name } = slaStore.getState();

		errors[ALERT_MESSAGE] = '';
		errors[NAME] = validateName(name);

		this.setState({ errors });
	}

	reloadNodes() {
		const { processId } = this.props;

		this.setState({
			loading: true
		});

		return nodeStore.fetchNodes(processId).then(() => {
			this.setState({
				loading: false
			});
		});
	}

	render() {
		const { defaultDelta } = this.context;
		const { errors, loading = false, redirectToSLAList = false } = this.state;
		const { id, processId, query } = this.props;

		if (loading) {
			return <LoadingState />;
		}

		if (redirectToSLAList) {
			if (id) {
				return <BackRedirect />;
			}

			return (
				<Redirect
					to={{
						pathname: `/slas/${processId}/${defaultDelta}/1`,
						search: query
					}}
				/>
			);
		}

		const { calendars } = calendarStore.getState();

		const showCalendarField = calendars.length > 1;

		const {
			calendarKey = calendarStore.defaultCalendar.key,
			days,
			description,
			hours,
			name,
			pauseNodeKeys: { nodeKeys: pauseNodeKeys },
			startNodeKeys: { nodeKeys: startNodeKeys },
			status,
			stopNodeKeys: { nodeKeys: stopNodeKeys }
		} = slaStore.getState();
		const daysMask = createNumberMask({
			includeThousandsSeparator: false,
			prefix: ''
		});
		const onChangeHandler = validationFunc => evt => {
			this.handleChange(evt, validationFunc);
		};

		const pauseNodes = nodeStore.getPauseNodes(startNodeKeys, stopNodeKeys);
		const startNodes = nodeStore.getStartNodes(pauseNodeKeys, stopNodeKeys);
		const stopNodes = nodeStore.getStopNodes(pauseNodeKeys, startNodeKeys);

		const [pauseNodeTagIds, startNodeTagIds, stopNodeTagIds] = [
			pauseNodes.filter(({ id }) => pauseNodeKeys.find(node => node.id == id)),
			startNodes.filter(({ compositeId }) =>
				startNodeKeys.find(
					node => `${node.id}:${node.executionType}` === compositeId
				)
			),
			stopNodes.filter(({ compositeId }) =>
				stopNodeKeys.find(
					node => `${node.id}:${node.executionType}` === compositeId
				)
			)
		].map(nodeKeys => nodeKeys.map(({ compositeId }) => `${compositeId}`));

		return (
			<div className="sla-form">
				{errors[ALERT_MESSAGE] && (
					<div className="alert-container">
						<div className="alert alert-danger" role="alert">
							<span className="alert-indicator">
								<Icon iconName="exclamation-full" />
							</span>

							<strong className="lead">{Liferay.Language.get('error')}</strong>

							<span>{errors[ALERT_MESSAGE]}</span>
						</div>
					</div>
				)}

				{status === 2 && (
					<div className="alert-container">
						<div className="alert alert-danger alert-dismissible" role="alert">
							<span className="alert-indicator">
								<Icon iconName="exclamation-full" />
							</span>

							<strong className="lead">{Liferay.Language.get('error')}</strong>

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
				)}

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
									id="sla_name"
									maxLength={75}
									name="name"
									onChange={onChangeHandler(this.onNameChanged)}
									type="text"
									value={name}
								/>

								{errors[NAME] && <FieldError error={errors[NAME]} />}
							</div>

							<div className="col col-sm-7 form-group">
								<FieldLabel
									fieldId="sla_description"
									text={Liferay.Language.get('description')}
								/>

								<input
									className="form-control"
									id="sla_description"
									name="description"
									onChange={onChangeHandler()}
									onFocus={this.hideDropLists}
									type="text"
									value={description}
								/>
							</div>
						</div>

						<h3 className="sheet-subtitle">
							<FieldLabel
								fieldId="sla_time_frame"
								text={Liferay.Language.get('time-frame').toUpperCase()}
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
									{Liferay.Language.get('time-will-begin-counting-when')}
								</div>

								<MultiSelect
									data={startNodes}
									fieldId="compositeId"
									onChangeTags={this.handleStartNodes}
									selectedTagsId={startNodeTagIds}
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
									{Liferay.Language.get('time-wont-be-considered-when')}
								</div>

								<MultiSelect
									data={pauseNodes}
									fieldId="compositeId"
									onChangeTags={this.handlePauseNodesChange}
									selectedTagsId={pauseNodeTagIds}
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
									{Liferay.Language.get('time-will-stop-counting-when')}
								</div>

								<MultiSelect
									data={stopNodes}
									fieldId="compositeId"
									onChangeTags={this.handleStopNodesChange}
									selectedTagsId={stopNodeTagIds}
								/>

								{errors[STOP_NODE_KEYS] && (
									<FieldError error={errors[STOP_NODE_KEYS]} />
								)}
							</div>
						</div>

						<h3 className="sheet-subtitle">
							<FieldLabel
								fieldId="sla_description"
								required
								text={Liferay.Language.get('duration').toUpperCase()}
							/>
						</h3>

						<div className="sheet-text">
							{showCalendarField
								? Liferay.Language.get(
									'define-the-sla-duration-and-calendar-format'
								  )
								: Liferay.Language.get('define-the-sla-duration')}
						</div>

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
									id="sla_duration_days"
									mask={daysMask}
									maxLength={4}
									name={DAYS}
									onChange={onChangeHandler(this.onDurationChanged)}
									onFocus={this.hideDropLists}
									value={days}
								/>

								{errors[DURATION] && <FieldError error={errors[DURATION]} />}

								<div className="form-text">
									{Liferay.Language.get('enter-a-whole-number')}
								</div>
							</div>

							<div
								className={`col col-sm-3 form-group ${
									errors[DURATION] || errors[HOURS] ? 'has-error' : ''
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
									onBlur={this.onHoursBlurred}
									onChange={onChangeHandler(this.onDurationChanged)}
									placeholder="00:00"
									value={hours}
								/>

								{errors[DURATION] && <FieldError error={errors[DURATION]} />}
								{errors[HOURS] && <FieldError error={errors[HOURS]} />}
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
										onChange={this.handleChange}
										value={calendarKey}
									>
										{calendars.map((calendar, index) => (
											<option key={index} value={calendar.key}>
												{calendar.title}

												{calendar.defaultCalendar &&
													` (${Liferay.Language.get('system-default')})`}
											</option>
										))}
									</select>
								</div>
							)}
						</div>
					</div>

					<div className="sheet-footer sheet-footer-btn-block-sm-down">
						<div className="btn-group">
							<div className="btn-group-item">
								<button
									className="btn btn-primary"
									onClick={this.handleSubmit}
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
				</form>
			</div>
		);
	}
}

SLAForm.contextType = AppContext;
export default SLAForm;