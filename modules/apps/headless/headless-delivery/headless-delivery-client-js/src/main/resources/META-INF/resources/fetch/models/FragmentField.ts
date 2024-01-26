/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FragmentFieldAction } from './FragmentFieldAction';
import type { FragmentFieldBackgroundImage } from './FragmentFieldBackgroundImage';
import type { FragmentFieldHTML } from './FragmentFieldHTML';
import type { FragmentFieldImage } from './FragmentFieldImage';
import type { FragmentFieldText } from './FragmentFieldText';
/**
 * Represents a fragment field.
 */
export type FragmentField = {
    /**
     * The fragment field's ID.
     */
    id?: string;
    /**
     * The fragment field's value.
     */
    value?: (FragmentFieldAction | FragmentFieldBackgroundImage | FragmentFieldHTML | FragmentFieldImage | FragmentFieldText);
};

