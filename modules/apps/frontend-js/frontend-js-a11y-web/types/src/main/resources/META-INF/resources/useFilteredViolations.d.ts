export function FilteredViolationsContextProvider({
	children,
	value,
}: {
	children: any;
	value: any;
}): JSX.Element;
export namespace TYPES {
	const CATEGORY_ADD: string;
	const CATEGORY_REMOVE: string;
	const IMPACT_ADD: string;
	const IMPACT_REMOVE: string;
}
export function useFilteredViolationsDispatch(): () => void;
