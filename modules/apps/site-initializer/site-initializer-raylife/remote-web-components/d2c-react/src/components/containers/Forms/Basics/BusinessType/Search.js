import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import useDebounce from 'lodash.debounce';

/* eslint-disable react-hooks/exhaustive-deps */
import React, {useCallback, useEffect} from 'react';
import {useFormContext} from 'react-hook-form';

import {TIP_EVENT} from '../../../../../events';
import {useBusinessTypes} from '../../../../../hooks/useBusinessTypes';
import {useCustomEvent} from '../../../../../hooks/useCustomEvent';
import {useStepWizard} from '../../../../../hooks/useStepWizard';
import {useTriggerContext} from '../../../../../hooks/useTriggerContext';
import {WarningBadge} from '../../../../fragments/Badges/Warning';
import {SearchInput} from '../../../../fragments/Forms/Input/Search';
import {BusinessTypeRadioGroup} from './RadioGroup';

const MAX_LENGTH_TO_TRUNCATE = 28;

export const BusinessTypeSearch = ({form}) => {
	const {
		formState: {errors},
		register,
		setValue,
	} = useFormContext();
	const [dispatchEvent] = useCustomEvent(TIP_EVENT);

	const {selectedStep} = useStepWizard();
	const {businessTypes, isError, isLoading, reload} = useBusinessTypes();
	const {isSelected, updateState} = useTriggerContext();

	const templateName = 'i-am-unable-to-find-my-industry';
	const selectedTrigger = isSelected(templateName);

	useEffect(() => {
		onSearch(form?.basics?.businessSearch);
	}, [form?.basics?.businessSearch]);

	const onSearch = useCallback(
		useDebounce((searchTerm = '') => {
			if (!searchTerm.length) {
				setValue('basics.businessCategoryId', '');
			}

			return reload(searchTerm);
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
						There are no results for "
						{truncateSearch(form?.basics?.businessSearch)}
						". Please try a different search.
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
						onClick={() => onSearch(form?.basics?.businessSearch)}
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
