/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_FormFieldOption} from './HeadlessForm_v1_0_FormFieldOption';
import type {HeadlessForm_v1_0_Grid} from './HeadlessForm_v1_0_Grid';
import type {HeadlessForm_v1_0_Validation} from './HeadlessForm_v1_0_Validation';
export type HeadlessForm_v1_0_FormField = {
	'autocomplete'?: boolean;
	'dataSourceType'?: string;
	'dataType'?: string;
	'displayStyle'?: string;
	'formFieldOptions'?: Array<HeadlessForm_v1_0_FormFieldOption>;
	'grid'?: HeadlessForm_v1_0_Grid;
	'hasFormRules'?: boolean;
	'id'?: number;
	'immutable'?: boolean;
	'inline'?: boolean;
	'inputControl'?: string;
	'label'?: string;
	'label_i18n'?: Record<string, string>;
	'localizable'?: boolean;
	'multiple'?: boolean;
	'name'?: string;
	'placeholder'?: string;
	'predefinedValue'?: string;
	'predefinedValue_i18n'?: Record<string, string>;
	'readOnly'?: boolean;
	'repeatable'?: boolean;
	'required'?: boolean;
	'showAsSwitcher'?: boolean;
	'showLabel'?: boolean;
	'style'?: string;
	'text'?: string;
	'text_i18n'?: Record<string, string>;
	'tooltip'?: string;
	'validation'?: HeadlessForm_v1_0_Validation;
	readonly 'x-class-name'?: string;
};
