/// <reference types="react" />

interface ConfigurationContainerProps {
	values: Partial<ObjectDefinition>;
	hasUpdateObjectDefinitionPermission: boolean;
	setValues: (values: Partial<ObjectDefinition>) => void;
	isApproved: boolean;
}
export declare function ConfigurationContainer({
	hasUpdateObjectDefinitionPermission,
	isApproved,
	setValues,
	values,
}: ConfigurationContainerProps): JSX.Element;
export {};
