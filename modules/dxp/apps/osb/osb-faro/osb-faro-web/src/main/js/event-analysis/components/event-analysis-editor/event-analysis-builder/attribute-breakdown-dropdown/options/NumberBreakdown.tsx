import ClayButton from '@clayui/button';
import Form, {
	validateGreaterThanZero,
	validateIsInteger,
	validateRequired
} from 'shared/components/form';
import React from 'react';
import {createNumberBreakdown} from 'event-analysis/utils/utils';
import {IBreakdownProps} from 'event-analysis/utils/types';
import {sequence} from 'shared/util/promise';

const DEFAULT_NUMBER_BIN = 10;

const NumberBreakdown: React.FC<IBreakdownProps> = ({
	attributeId,
	attributeOwnerType,
	breakdown,
	description,
	displayName,
	onSubmit
}) => {
	const getInitialValues = () => {
		if (breakdown) {
			const {binSize} = breakdown;

			return {binSize};
		}

		return {
			binSize: DEFAULT_NUMBER_BIN
		};
	};

	return (
		<Form
			enableReinitialize
			initialValues={getInitialValues()}
			isInitialValid
			onSubmit={({binSize}) => {
				onSubmit(
					createNumberBreakdown({
						attributeId,
						attributeType: attributeOwnerType,
						binSize: Number(binSize),
						description,
						displayName
					})
				);
			}}
		>
			{({handleSubmit, isValid}) => (
				<Form.Form onSubmit={handleSubmit}>
					<div className='options-body'>
						<Form.Group autoFit>
							<Form.GroupItem>
								<Form.Input
									label={Liferay.Language.get(
										'group-numbers-by'
									)}
									name='binSize'
									step='any'
									type='number'
									validate={sequence([
										validateRequired,
										validateGreaterThanZero,
										validateIsInteger
									])}
								/>
							</Form.GroupItem>
						</Form.Group>
					</div>

					<div className='options-footer'>
						<ClayButton
							block
							className='button-root'
							disabled={!isValid}
							displayType='primary'
							type='submit'
						>
							{Liferay.Language.get('done')}
						</ClayButton>
					</div>
				</Form.Form>
			)}
		</Form>
	);
};

export default NumberBreakdown;
