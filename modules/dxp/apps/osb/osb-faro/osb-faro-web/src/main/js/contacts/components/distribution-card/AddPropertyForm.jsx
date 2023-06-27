import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import Form, {
	toPromise,
	validateMaxLength,
	validateRequired
} from 'shared/components/form';
import FormSelectFieldInput from 'contacts/components/form/SelectFieldInput';
import Loading, {Align} from 'shared/components/Loading';
import React, {useRef, useState} from 'react';
import {DistributionTab} from 'shared/util/records';
import {FieldContexts, FieldTypes} from 'shared/util/constants';
import {
	getContextLabel,
	numberOfBinsMask
} from 'contacts/components/Distribution';
import {INDIVIDUALS_DASHBOARD_DISTRUBTIONS_KEY} from 'shared/actions/distributions';
import {isBlank} from 'shared/util/util';
import {List} from 'immutable';
import {sequence} from 'shared/util/promise';
import {sub} from 'shared/util/lang';

const validateNumberOfBins = numberOfBins => {
	let error = '';

	if (isBlank(numberOfBins)) {
		error = Liferay.Language.get('required');
	} else if (numberOfBins > 10 || numberOfBins < 1) {
		return Liferay.Language.get('number-of-bins-must-be-between-1-and-10');
	}

	return error;
};

const AddPropertyForm = ({
	defaultContext = FieldContexts.Demographics,
	distributionKey,
	groupId,
	onCancel,
	onSubmit,
	tabsIList = new List()
}) => {
	const _formRef = useRef();
	const _formSelectFieldInputRef = useRef();

	const [showBin, setShowBin] = useState(false);

	const focusSelectFieldInput = () => {
		_formSelectFieldInputRef.current.focus();
	};

	const handleSubmit = ({
		numberOfBins,
		property: {context, id, rawType},
		title
	}) => {
		const {setSubmitting} = _formRef.current;

		onSubmit(
			DistributionTab({
				context,
				id: title.toLowerCase(),
				numberOfBins,
				propertyId: id,
				propertyType: rawType,
				title
			})
		)
			.then(() => {
				analytics.track('Created Distribution Query', {
					dataType: rawType,
					distributionType:
						context === FieldContexts.Demographics
							? 'individual'
							: 'account',
					numberOfBins: Number(numberOfBins),
					pageType:
						distributionKey ===
						INDIVIDUALS_DASHBOARD_DISTRUBTIONS_KEY
							? 'individualOverview'
							: 'segmentOverview'
				});

				setSubmitting(false);
			})
			.catch(() => setSubmitting(false));
	};

	const handleValidateName = value => {
		let error = '';

		if (
			tabsIList.some(tabIMap => tabIMap.get('id') === value.toLowerCase())
		) {
			error = Liferay.Language.get('tab-name-already-exists');
		}

		return toPromise(error);
	};

	const handleValidateProperty = property => {
		let error = '';

		if (!property) {
			error = Liferay.Language.get('required');
		} else if (defaultContext !== property.context) {
			focusSelectFieldInput();

			return sub(Liferay.Language.get('invalid-breakdown-for-x'), [
				getContextLabel(defaultContext)
			]);
		}

		return error;
	};

	return (
		<Card.Body className='add-property-form-root'>
			<div className='row d-flex flex-column flex-grow-1'>
				<div className='col-xl-5 d-flex flex-column flex-grow-1'>
					<div className='description'>
						<h4>
							{Liferay.Language.get(
								'add-a-breakdown-by-individual-attribute'
							)}
						</h4>

						<p>
							{Liferay.Language.get(
								'breakdown-will-display-the-top-10-results-or-be-divided-into-a-maximum-of-10-bins-depending-on-your-attribute-type'
							)}
						</p>
					</div>

					<Form
						initialValues={{
							numberOfBins: 0,
							property: null,
							title: ''
						}}
						onSubmit={handleSubmit}
						ref={_formRef}
					>
						{({handleSubmit, isSubmitting, isValid}) => (
							<Form.Form
								className='d-flex flex-column flex-grow-1 justify-content-between'
								onSubmit={handleSubmit}
							>
								<div className='form-items'>
									<Form.Group autoFit>
										<Form.GroupItem shrink>
											<FormSelectFieldInput
												context={defaultContext}
												groupId={groupId}
												label={Liferay.Language.get(
													'attribute[noun]'
												)}
												name='property'
												onSelect={({rawType}) =>
													setShowBin(
														rawType ===
															FieldTypes.Number
													)
												}
												ref={_formSelectFieldInputRef}
												required
												validate={
													handleValidateProperty
												}
											/>
										</Form.GroupItem>

										{showBin && (
											<Form.GroupItem>
												<Form.Input
													className='number-of-bins-input'
													label={Liferay.Language.get(
														'bins'
													)}
													mask={numberOfBinsMask}
													name='numberOfBins'
													required
													showSuccess={false}
													validate={
														validateNumberOfBins
													}
												/>
											</Form.GroupItem>
										)}
									</Form.Group>

									<Form.Group autoFit>
										<Form.GroupItem>
											<Form.Input
												label={Liferay.Language.get(
													'breakdown-name'
												)}
												name='title'
												required
												validate={sequence([
													validateRequired,
													validateMaxLength(255),
													handleValidateName
												])}
											/>
										</Form.GroupItem>
									</Form.Group>
								</div>

								<div className='form-navigation'>
									<ClayButton
										className='button-root'
										disabled={!isValid || isSubmitting}
										displayType='primary'
										type='submit'
									>
										{isSubmitting && (
											<Loading align={Align.Left} />
										)}

										{Liferay.Language.get('save')}
									</ClayButton>

									{!!tabsIList.size && (
										<ClayButton
											className='button-root'
											displayType='secondary'
											onClick={onCancel}
										>
											{Liferay.Language.get('cancel')}
										</ClayButton>
									)}
								</div>
							</Form.Form>
						)}
					</Form>
				</div>
			</div>
		</Card.Body>
	);
};

export default AddPropertyForm;
