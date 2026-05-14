import * as API from 'shared/api';
import VocabularyDisplay from 'segment/components/criteria-card/display-components/VocabularyDisplay';
import VocabularyInput from '../inputs/VocabularyInput';
import {createVocabularyProperty} from '../utils/utils';
import {CustomFunctionOperators, NotOperators} from '../utils/constants';
import {RemoteCriterionType} from './RemoteCriterionType';

export const vocabularyCriterionType: RemoteCriterionType = {
	get api() {
		return API.vocabularies.search;
	},
	createProperty: createVocabularyProperty,
	DisplayComponent: VocabularyDisplay,
	idProperty: 'vocabularies/id',
	InputComponent: VocabularyInput,
	nameProperty: 'vocabularies/name',
	negativeOperator: NotOperators.NotVocabulariesFilter,
	operators: new Set([
		CustomFunctionOperators.VocabulariesFilter,
		NotOperators.NotVocabulariesFilter
	]),
	positiveOperator: CustomFunctionOperators.VocabulariesFilter,
	propertyKey: 'vocabulary',
	supportsCategories: true
};
