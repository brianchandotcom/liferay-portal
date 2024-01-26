/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FragmentSettingsAllowed } from './FragmentSettingsAllowed';
import type { FragmentSettingsUnallowed } from './FragmentSettingsUnallowed';
/**
 * Represent a definition of a Page drop zone.
 */
export type PageDropZoneDefinition = {
    /**
     * The page drop zone's allowed or unallowed fragments.
     */
    fragmentSettings?: (FragmentSettingsAllowed | FragmentSettingsUnallowed);
};

