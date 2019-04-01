import { ALERT_MESSAGE, DAYS, DURATION, HOURS, NAME } from './Constants';
import { AppContext, AppStatus } from '../AppContext';
import {
	BackLink,
	BackRedirect
} from '../../shared/components/router/routerWrapper';
import {
	durationAsMilliseconds,
	formatHours,
	getDurationValues
} from '../../shared/util/duration';
import {
	hasErrors,
	validateDays,
	validateDuration,
	validateHours,
	validateName
} from './util/slaFormUtil';
import autobind from 'autobind-decorator';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import FieldError from './form/fieldError';
import FieldLabel from './form/fieldLabel';
import Icon from '../../shared/components/Icon';
import LoadingState from '../../shared/components/empty-state/LoadingState';
import MaskedInput from 'react-text-mask';
import { openErrorToast } from '../../shared/util/toast';
import React from 'react';

/**
 * SLA form component.
 * @extends React.Component
 */
class SLAForm extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			days: null,
			description: '',
			errors: {},
			hours: '',
			loading: false,
			name: '',
			redirectToSlaList: false
		};
	}

	componentWillMount() {
		if (this.props.id) {
			return this.loadData();
		}
		else {
			this.context.setTitle(Liferay.Language.get('new-sla'));
		}
	}

	@autobind
	handleChange(
		{
			target: { name, value = '' }
		},
		callback
	) {
		this.setState({ [name]: value }, callback);
	}

	@autobind
	handleSubmit() {
		const { days, description, hours, name } = this.state;
		const { errors } = this.state;

		errors[DAYS] = validateDays(days);
		errors[DURATION] = validateDuration(days, hours);
		errors[HOURS] = validateHours(hours);
		errors[NAME] = validateName(name);

		if (hasErrors(errors)) {
			errors[ALERT_MESSAGE] = Liferay.Language.get(
				'please-fill-in-the-required-fields'
			);

			this.setState({ errors });
		}
		else {
			const { client } = this.context;
			const { id, processId } = this.props;
			const duration = durationAsMilliseconds(days, hours);

			let submit;

			if (id) {
				submit = body => client.put(`/slas/${id}`, body);
			}
			else {
				submit = body => client.post(`/processes/${processId}/slas`, body);
			}

			errors[ALERT_MESSAGE] = '';

			return submit({
				description,
				duration,
				name,
				processId
			})
				.then(() => {
					const status = id ? AppStatus.slaUpdated : AppStatus.slaSaved;

					this.context.setStatus(status, () => {
						this.setState({ redirectToSlaList: true });
					});
				})
				.catch(result => {
					const {
						response: { data }
					} = result;

					if (data) {
						const errorKey = data.fieldName || ALERT_MESSAGE;

						errors[errorKey] = data.message;
						this.setState({ errors });
					}
					else {
						openErrorToast(result);
					}
				});
		}
	}

	loadData() {
		const { client } = this.context;
		const { id } = this.props;

		this.setState({ loading: true });

		return client
			.get(`/slas/${id}`)
			.then(result => {
				const { description = '', duration, name } = result.data;
				const { days, hours, minutes } = getDurationValues(duration);
				const formattedHours = formatHours(hours, minutes);

				this.context.setTitle(Liferay.Language.get(name));

				this.setState({
					days,
					description,
					hours: formattedHours,
					loading: false,
					name
				});
			})
			.catch(openErrorToast);
	}

	@autobind
	onDaysBlurred() {
		const { days, errors } = this.state;

		errors[ALERT_MESSAGE] = '';
		errors[DAYS] = validateDays(days);

		this.setState({ errors });
	}

	@autobind
	onDurationChanged() {
		const { days, errors, hours } = this.state;

		errors[ALERT_MESSAGE] = '';
		errors[DURATION] = validateDuration(days, hours);

		this.setState({ errors });
	}

	@autobind
	onHoursBlurred() {
		const { errors, hours } = this.state;

		errors[ALERT_MESSAGE] = '';
		errors[HOURS] = validateHours(hours);

		this.setState({ errors });
	}

	@autobind
	onNameChanged() {
		const { errors, name } = this.state;

		errors[ALERT_MESSAGE] = '';
		errors[NAME] = validateName(name);

		this.setState({ errors });
	}

	render() {
		const daysMask = createNumberMask({
			includeThousandsSeparator: false,
			prefix: ''
		});
		const { id } = this.props;
		const { errors, loading, redirectToSlaList } = this.state;
		const onChangeHandler = validationFunc => evt => {
			this.handleChange(evt, validationFunc);
		};

		if (loading === true) {
			return <LoadingState />;
		}

		if (redirectToSlaList === true) {
			return <BackRedirect />;
		}

		return (
			<div className="sla-form">
				{(errors[ALERT_MESSAGE] || errors[DURATION]) && (
					<div className="alert-container">
						<div className="alert alert-danger" role="alert">
							<span className="alert-indicator">
								<Icon iconName="exclamation-full" />
							</span>

							<strong className="lead">{Liferay.Language.get('error')}</strong>

							<span>{errors[ALERT_MESSAGE] || errors[DURATION]}</span>
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
								className={`form-group col col-sm-5 ${
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
									name="name"
									onChange={onChangeHandler(this.onNameChanged)}
									type="text"
									value={this.state.name}
								/>

								{errors[NAME] && <FieldError error={errors[NAME]} />}
							</div>

							<div className="form-group col col-sm-7">
								<FieldLabel
									fieldId="sla_description"
									text={Liferay.Language.get('description')}
								/>

								<input
									className="form-control"
									id="sla_description"
									name="description"
									onChange={onChangeHandler()}
									type="text"
									value={this.state.description}
								/>
							</div>
						</div>

						<h3 className="sheet-subtitle">
							<FieldLabel
								fieldId="sla_description"
								required
								text={Liferay.Language.get('duration-time').toUpperCase()}
							/>
						</h3>

						<div className="sheet-text">
							{`${Liferay.Language.get(
								'define-a-duration-time-to-be-met'
							)} ${Liferay.Language.get(
								'enter-at-least-one-of-the-following-fields'
							)}`}
						</div>

						<div className={`row ${errors[DURATION] ? 'has-error' : ''}`}>
							<div
								className={`form-group col col-sm-5 ${
									errors[DAYS] ? 'has-error' : ''
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
									name={DAYS}
									onBlur={this.onDaysBlurred}
									onChange={onChangeHandler(this.onDurationChanged)}
									value={this.state.days}
								/>

								<div className="form-text">
									{Liferay.Language.get('enter-a-whole-number')}
								</div>

								{errors[DAYS] && <FieldError error={errors[DAYS]} />}
							</div>

							<div
								className={`form-group col col-sm-3 ${
									errors[HOURS] ? 'has-error' : ''
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
									value={this.state.hours}
								/>

								{errors[HOURS] && <FieldError error={errors[HOURS]} />}
							</div>
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