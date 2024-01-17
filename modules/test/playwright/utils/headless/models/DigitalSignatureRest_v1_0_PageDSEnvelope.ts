/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DigitalSignatureRest_v1_0_DSEnvelope} from './DigitalSignatureRest_v1_0_DSEnvelope';
import type {DigitalSignatureRest_v1_0_Facet} from './DigitalSignatureRest_v1_0_Facet';
export type DigitalSignatureRest_v1_0_PageDSEnvelope = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<DigitalSignatureRest_v1_0_DSEnvelope>;
	facets?: Array<DigitalSignatureRest_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
