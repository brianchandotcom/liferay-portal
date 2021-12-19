import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';

import classNames from 'classnames';
import useDebounce from 'lodash.debounce';

import React, {useCallback, useEffect, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {WarningBadge} from '../../../../../../../common/components/fragments/Badges/Warning';
import {SearchInput} from '../../../../../../../common/components/fragments/Forms/Input/Search';
import {useCustomEvent} from '../../../../../../../common/hooks/useCustomEvent';
import {calculatePercentage} from '../../../../../../../common/utils';
import {TIP_EVENT} from '../../../../../../../common/utils/events';
import {useBusinessTypes} from '../../../../../hooks/useBusinessTypes';
import {useStepWizard} from '../../../../../hooks/useStepWizard';
import {useTriggerContext} from '../../../../../hooks/useTriggerContext';
import {AVAILABLE_STEPS, TOTAL_OF_FIELD} from '../../../../../utils/constants';
import {getLoadedContentFlag} from '../../../../../utils/util';

import {BusinessTypeRadioGroup} from './RadioGroup';

const MAX_LENGTH_TO_TRUNCATE = 28;

export function BusinessTypeSearch({form, setNewSelectedProduct}) {
	const {register, setValue} = useFormContext();
	const [dispatchEvent] = useCustomEvent(TIP_EVENT);

	const {selectedStep, setPercentage} = useStepWizard();
	const {businessTypes, isError, reload} = useBusinessTypes();
	const {isSelected, updateState} = useTriggerContext();
	const [isLoading, setIsLoading] = useState(false);
	const {applicationId, backToEdit} = getLoadedContentFlag();

	const templateName = 'i-am-unable-to-find-my-industry';
	const selectedTrigger = isSelected(templateName);
	let auxSearchToChange = '';

	useEffect(() => {
		auxSearchToChange = form?.basics?.businessSearch;
		onSearch(form?.basics?.businessSearch);
		setIsLoading(true);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [form?.basics?.businessSearch]);

	const onSearch = useCallback(
		useDebounce(async (searchTerm = '') => {
			if (!searchTerm || auxSearchToChange !== searchTerm) {
				setValue('basics.businessCategoryId', '');
				auxSearchToChange = searchTerm;
			}
			await reload(searchTerm);
			if (!searchTerm) {
				if (applicationId || backToEdit) {
					setPercentage(
						calculatePercentage(
							TOTAL_OF_FIELD.BASICS - 1,
							TOTAL_OF_FIELD.BASICS
						),
						AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
					);
				}
				setValue('basics.properties.businessClassCode', '');
				setValue('basics.properties.naics', '');
				setValue('basics.properties.segment', '');
				setValue('basics.product', '');
			}
			setIsLoading(false);
		}, 500),
		[]
	);

	const truncateSearch = (text) => {
		if (!text || text.length <= MAX_LENGTH_TO_TRUNCATE) {
			return text;
		}

		return text.slice(0, MAX_LENGTH_TO_TRUNCATE) + '...';
	};

	const showInfoPanel = () => {
		updateState(templateName);
		dispatchEvent({
			hide: selectedTrigger,
			step: selectedStep,
			templateName,
		});
	};

	const infoPanelButton = () => (
		<ClayLabel
			className={classNames('btn-info-panel mt-3', {
				'label-inverse-primary': selectedTrigger,
				'label-tonal-primary': !selectedTrigger,
			})}
			onClick={showInfoPanel}
		>
			<div className="align-items-center d-flex mx-2">
				<span className="text-paragraph-sm">
					I am unable to find my industry
				</span>

				<span className="inline-item inline-item-after">
					<ClayIcon
						symbol={
							selectedTrigger
								? 'question-circle-full'
								: 'question-circle'
						}
					/>
				</span>
			</div>
		</ClayLabel>
	);

	const renderResults = () => {
		if (isLoading || !form?.basics?.businessSearch) {
			return;
		}

		if (isError) {
			return (
				<>
					<WarningBadge>{isError}</WarningBadge>
				</>
			);
		}

		if (businessTypes.length) {
			return (
				<>
					<BusinessTypeRadioGroup
						businessTypes={businessTypes}
						form={form}
						setNewSelectedProduct={setNewSelectedProduct}
					/>
					{infoPanelButton()}
				</>
			);
		}

		return (
			<>
				<WarningBadge>
					There are no results for &quot;
					{truncateSearch(form?.basics?.businessSearch)}
					&quot;. Please try a different search.
				</WarningBadge>
				{infoPanelButton()}
			</>
		);
	};

	return (
		<>
			<div className="mb-5">
				<SearchInput
					className="bg-neutral-1 font-weight-bold px-4 py-0 search text-neutral-10 text-paragraph-lg"
					defaultValue=""
					label="Search for your primary industry and then select it from the list."
					placeholder="Begin typing to show options..."
					required
					{...register('basics.businessSearch', {
						required:
							'Please, search for a business type in order to proceed.',
					})}
				>
					<ClayButton
						className="font-weight-bolder ml-3 search text-paragraph text-small-caps"
						displayType="primary"
						onClick={() => {
							onSearch(form?.basics?.businessSearch);
						}}
					>
						Search
					</ClayButton>
				</SearchInput>

				<p className="paragraph">
					i.e. Apartments, Coffee, Medical, Pet Stores, etc
				</p>
			</div>
			{renderResults()}
		</>
	);
}
