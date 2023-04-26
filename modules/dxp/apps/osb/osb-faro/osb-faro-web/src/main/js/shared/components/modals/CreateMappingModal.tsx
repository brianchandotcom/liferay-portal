import * as API from 'shared/api';
import ClayButton from '@clayui/button';
import Form, {
	toPromise,
	validatePattern,
	validateRequired
} from 'shared/components/form';
import getCN from 'classnames';
import Modal from 'shared/components/modal';
import React, {useRef} from 'react';
import {FieldContexts} from 'shared/util/constants';
import {noop, trim} from 'lodash';
import {sequence} from 'shared/util/promise';

const TYPES = [
	{
		name: Liferay.Language.get('text'),
		value: 'Text'
	},
	{
		name: Liferay.Language.get('number'),
		value: 'Number'
	},
	{
		name: Liferay.Language.get('boolean'),
		value: 'Boolean'
	},
	{
		name: Liferay.Language.get('date'),
		value: 'Date'
	}
];

interface ICreateMappingModalProps
	extends React.HTMLAttributes<HTMLDivElement> {
	groupId: string;
	onClose: () => void;
	onSubmit?: (fieldMapping: any) => void;
}

const CreateMappingModal: React.FC<ICreateMappingModalProps> = ({
	className,
	groupId,
	onClose,
	onSubmit = noop
}) => {
	const _cachedNameValues = useRef(new Map());

	const handleSubmit = (formValues: {
		name: string;
		type: typeof TYPES[number];
	}) => {
		API.fieldMappings
			.create({
				groupId,
				name: trim(formValues.name),
				type: formValues.type.value
			})
			.then(onSubmit)
			.catch(noop);
	};

	const validateUniqueName = (value: string) => {
		let error = '';

		if (_cachedNameValues.current.has(value)) {
			error = _cachedNameValues.current.get(value);
		} else {
			error = API.fieldMappings
				.search({
					context: FieldContexts.Demographics,
					cur: 1,
					delta: 1,
					fieldName: value,
					groupId,
					orderByType: ''
				})
				.then(result => {
					let retVal = '';
					if (result.total) {
						retVal = Liferay.Language.get(
							'a-field-already-exists-with-that-name.-please-enter-a-different-name'
						);
					}

					_cachedNameValues.current.set(value, retVal);

					return retVal;
				})
				.catch(() => Liferay.Language.get('could-not-validate'));
		}

		return toPromise(error);
	};

	return (
		<Modal
			className={getCN('create-mapping-modal-root', className)}
			size='lg'
		>
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('create-new-csv-field')}
			/>

			<Form
				initialValues={{name: '', type: {name: '', value: null}}}
				onSubmit={handleSubmit}
			>
				{({handleSubmit, isValid}) => (
					<Form.Form onSubmit={handleSubmit}>
						<Form.Group autoFit>
							<Form.Input
								label={Liferay.Language.get('new-field-name')}
								name='name'
								placeholder={Liferay.Language.get(
									'enter-new-field-name'
								)}
								validate={sequence([
									validateRequired,
									validatePattern(
										/^[A-Za-z_][\w]{0,126}[A-Za-z0-9]$/,
										Liferay.Language.get(
											'field-name-must-start-with-a-letter-or-underscore-followed-by-at-most-127-letters-numbers-or-underscores'
										)
									),
									validateUniqueName
								])}
							/>

							<div>
								<label>
									{Liferay.Language.get('field-type')}
								</label>

								<Form.SearchableSelect
									buttonPlaceholder={Liferay.Language.get(
										'select'
									)}
									caretDouble
									items={TYPES}
									name='type'
									showSearch={false}
									validate={validateRequired}
								/>
							</div>
						</Form.Group>

						<Modal.Footer>
							<ClayButton
								className='button-root'
								displayType='secondary'
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								className='button-root'
								disabled={!isValid}
								displayType='primary'
								type='submit'
							>
								{Liferay.Language.get('create')}
							</ClayButton>
						</Modal.Footer>
					</Form.Form>
				)}
			</Form>
		</Modal>
	);
};

export default CreateMappingModal;
