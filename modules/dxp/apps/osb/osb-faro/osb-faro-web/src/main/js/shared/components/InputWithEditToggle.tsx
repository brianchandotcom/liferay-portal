import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import Form from 'shared/components/form';
import getCN from 'classnames';
import Label from 'shared/components/form/Label';
import Loading, {Align} from 'shared/components/Loading';
import Promise from 'metal-promise';
import React from 'react';
import {Formik} from 'formik';
interface IInputWithEditToggleProps {
	className?: string;
	editable: boolean;
	inputWidth?: number;
	label: string;
	name?: string;
	onSubmit: (value, name) => typeof Promise;
	required: boolean;
	validate: typeof Promise;
	value: string;
}

interface IInputWithEditToggleState {
	editing: boolean;
}

export default class InputWithEditToggle extends React.Component<
	IInputWithEditToggleProps,
	IInputWithEditToggleState
> {
	static defaultProps = {
		editable: true,
		name: 'name',
		required: false
	};

	state = {
		editing: false
	};

	_formRef = React.createRef<Formik>();

	@autobind
	handleSubmit(values) {
		const {name, onSubmit} = this.props;

		const {
			resetForm,
			setSubmitting
		} = this._formRef.current.getFormikActions();

		if (onSubmit) {
			onSubmit(values[name], name)
				.then(() => {
					setSubmitting(false);

					resetForm();

					this.setState({editing: false});
				})
				.catch(err => {
					if (!err.IS_CANCELLATION_ERROR) {
						setSubmitting(false);
					}
				});
		}
	}

	@autobind
	handleEditToggle() {
		this.setState({
			editing: !this.state.editing
		});
	}

	render() {
		const {
			props: {
				className,
				editable,
				inputWidth,
				label,
				name,
				required,
				validate,
				value
			},
			state: {editing}
		} = this;

		return (
			<div
				className={getCN(
					'input-with-edit-toggle-root',
					'definition-item-root',
					className
				)}
			>
				<Form
					initialValues={{[name]: value}}
					onSubmit={this.handleSubmit}
					ref={this._formRef}
				>
					{({handleSubmit, isSubmitting, isValid, resetForm}) => (
						<Form.Form
							className='input-with-edit-toggle-editor'
							onSubmit={handleSubmit}
						>
							{label && (
								<Label required={required}>{label}</Label>
							)}

							<Form.Group autoFit className='align-items-center'>
								<Form.Input
									contentAfter={
										editing ? (
											<>
												<ClayButton
													aria-label={Liferay.Language.get(
														'cancel'
													)}
													className='button-root'
													displayType='secondary'
													onClick={() => {
														this.handleEditToggle();

														resetForm();
													}}
													size='sm'
												>
													<ClayIcon
														className='icon-root'
														symbol='times'
													/>
												</ClayButton>

												<ClayButton
													aria-label={Liferay.Language.get(
														'submit'
													)}
													className='button-root'
													disabled={!isValid}
													displayType='primary'
													size='sm'
													type='submit'
												>
													{isSubmitting && (
														<Loading
															align={Align.Left}
														/>
													)}

													<ClayIcon
														className='icon-root'
														symbol='check'
													/>
												</ClayButton>
											</>
										) : (
											<ClayButton
												aria-label={Liferay.Language.get(
													'edit'
												)}
												className='button-root'
												disabled={!editable}
												displayType='secondary'
												onClick={this.handleEditToggle}
												size='sm'
											>
												<ClayIcon
													className='icon-root'
													symbol='pencil'
												/>
											</ClayButton>
										)
									}
									disabled={
										!editing || !editable || isSubmitting
									}
									name={name}
									validate={validate}
									width={inputWidth}
								/>
							</Form.Group>
						</Form.Form>
					)}
				</Form>
			</div>
		);
	}
}
