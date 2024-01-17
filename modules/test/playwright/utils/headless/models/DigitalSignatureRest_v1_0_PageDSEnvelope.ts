/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DigitalSignatureRest_v1_0_DSEnvelope} from './DigitalSignatureRest_v1_0_DSEnvelope';
import type {DigitalSignatureRest_v1_0_Facet} from './DigitalSignatureRest_v1_0_Facet';
export type DigitalSignatureRest_v1_0_PageDSEnvelope = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<DigitalSignatureRest_v1_0_DSEnvelope>;
	pageSize?: number;
	facets?: Array<DigitalSignatureRest_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
