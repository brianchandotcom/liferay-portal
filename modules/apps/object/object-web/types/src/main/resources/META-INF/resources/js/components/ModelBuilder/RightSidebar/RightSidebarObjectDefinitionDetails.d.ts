/// <reference types="react" />

import './RightSidebarObjectDefinitionDetails.scss';
import {KeyValuePair} from '../../ObjectDetails/EditObjectDetails';
interface RightSidebarObjectDefinitionDetailsProps {
	companyKeyValuePair: KeyValuePair[];
	siteKeyValuePair: KeyValuePair[];
}
export declare function RightSidebarObjectDefinitionDetails({
	companyKeyValuePair,
	siteKeyValuePair,
}: RightSidebarObjectDefinitionDetailsProps): JSX.Element;
export {};
