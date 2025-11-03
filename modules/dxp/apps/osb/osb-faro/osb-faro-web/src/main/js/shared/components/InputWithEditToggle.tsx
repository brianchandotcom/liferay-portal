import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import Form from 'shared/components/form';
import getCN from 'classnames';
import Label from 'shared/components/form/Label';
import Loading, {Align} from 'shared/components/Loading';
import React from 'react';
import {Text as ClayText} from '@clayui/core';
import {Formik} from 'formik';

export enum FontSize {
	Size1 = 1,
	Size2 = 2,
	Size3 = 3,
	Size4 = 4,
	Size5 = 5,
	Size6 = 6,
	Size7 = 7,
	Size8 = 8,
	Size9 = 9,
	Size10 = 10,
	Size11 = 11
}

interface IInputWithEditToggleProps {
	className?: string;
	hasBoldTitle?: boolean;
	hasDataSourceLabel?: boolean;
	editable: boolean;
	inputWidth?: number;
	isDataSourceConnected?: boolean;
	label?: string;
	name?: string;
	onSubmit: (value, name) => Promise<any>;
	required: boolean;
	validate: (value) => Promise<any>;
	value: string;
	valueFontSize?: FontSize;
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
		hasBoldTitle: false,
		hasDataSourceLabel: false,
		isDataSourceConnected: false,
		name: 'name',
		required: false,
		valueFontSize: FontSize.Size4
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
				hasBoldTitle,
				hasDataSourceLabel,
				inputWidth,
				isDataSourceConnected,
				label,
				name,
				required,
				validate,
				value,
				valueFontSize
			},
			state: {editing}
		} = this;

		const statusLabel = isDataSourceConnected
			? {displayType: 'success', text: Liferay.Language.get('connected')}
			: {
					displayType: 'secondary',
					text: Liferay.Language.get('disconnected')
			  };

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
							{hasDataSourceLabel && (
								<ClayLabel
									className='mb-2'
									displayType={statusLabel.displayType as any}
								>
									{statusLabel.text}
								</ClayLabel>
							)}

							{label && (
								<Label required={required}>{label}</Label>
							)}

							<Form.Group autoFit className='align-items-center'>
								{hasBoldTitle && !editing ? (
									<>
										<ClayText
											size={valueFontSize as any}
											weight='bold'
										>
											{value}
										</ClayText>

										<ClayButton
											aria-label={Liferay.Language.get(
												'edit'
											)}
											borderless
											className='button-root'
											disabled={!editable}
											displayType='secondary'
											onClick={this.handleEditToggle}
											outline
											size='sm'
										>
											<ClayIcon
												className='icon-root'
												symbol='pencil'
											/>
										</ClayButton>
									</>
								) : (
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
																align={
																	Align.Left
																}
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
													onClick={
														this.handleEditToggle
													}
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
											!editing ||
											!editable ||
											isSubmitting
										}
										name={name}
										validate={validate}
										width={inputWidth}
									/>
								)}
							</Form.Group>
						</Form.Form>
					)}
				</Form>
			</div>
		);
	}
}
