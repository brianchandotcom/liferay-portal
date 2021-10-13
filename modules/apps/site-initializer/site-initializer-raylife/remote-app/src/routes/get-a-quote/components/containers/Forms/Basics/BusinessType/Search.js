import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import useDebounce from 'lodash.debounce';

import React, {useCallback, useEffect, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {WarningBadge} from '~/common/components/fragments/Badges/Warning';
import {SearchInput} from '~/common/components/fragments/Forms/Input/Search';
import {useCustomEvent} from '~/common/hooks/useCustomEvent';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';
import {calculatePercentage} from '~/common/utils';
import {TIP_EVENT} from '~/common/utils/events';
import {useBusinessTypes} from '~/routes/get-a-quote/hooks/useBusinessTypes';
import {useStepWizard} from '~/routes/get-a-quote/hooks/useStepWizard';
import {useTriggerContext} from '~/routes/get-a-quote/hooks/useTriggerContext';
import {
	AVAILABLE_STEPS,
	TOTAL_OF_FIELD,
} from '~/routes/get-a-quote/utils/constants';

import {BusinessTypeRadioGroup} from './RadioGroup';

const MAX_LENGTH_TO_TRUNCATE = 28;

export const BusinessTypeSearch = ({form, setNewSelectedProduct}) => {
	const {
		formState: {errors},
		register,
		setValue,
	} = useFormContext();
	const [dispatchEvent] = useCustomEvent(TIP_EVENT);

	const {selectedStep, setPercentage} = useStepWizard();
	const {businessTypes, isError, reload} = useBusinessTypes();
	const {isSelected, updateState} = useTriggerContext();
	const [isLoading, setIsLoading] = useState(false);

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
				if (
					Storage.getItem(STORAGE_KEYS.BACK_TO_EDIT) &&
					JSON.parse(Storage.getItem(STORAGE_KEYS.BACK_TO_EDIT))
				) {
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
		<button
			className={classNames('btn badge bottom-list', {
				open: selectedTrigger,
			})}
			onClick={showInfoPanel}
			type="button"
		>
			I am unable to find my industry
			{selectedTrigger ? (
				<ClayIcon symbol="question-circle-full" />
			) : (
				<ClayIcon symbol="question-circle" />
			)}
		</button>
	);

	const renderResults = () => {
		if (isLoading || !form?.basics?.businessSearch) {
			return;
		}

		if (isError) {
			return <WarningBadge>{isError}</WarningBadge>;
		}

		if (!businessTypes.length) {
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
		}

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
	};

	return (
		<>
			<div>
				<SearchInput
					className="search"
					defaultValue=""
					error={errors?.basics?.businessSearch}
					label="Search for your primary industry and then select it from the list."
					placeholder="Begin typing to show options..."
					required
					{...register('basics.businessSearch', {
						required:
							'Please, search for a business type in order to proceed.',
					})}
				>
					<button
						className="btn btn-primary search"
						onClick={() => {
							onSearch(form?.basics?.businessSearch);
						}}
						type="button"
					>
						Search
					</button>
				</SearchInput>

				<p className="paragraph">
					i.e. Coffee shop, Plumber, Drop Shipping, Landscape, etc
				</p>
			</div>
			{renderResults()}
		</>
	);
};
