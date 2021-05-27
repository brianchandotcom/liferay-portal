export function A11yPanel({
	violations,
}: {
	violations?:
		| {
				id: string;
				impact: string;
				tags: string[];
				description: string;
				help: string;
				helpUrl: string;
				nodes: {
					any: {
						id: string;
						data: string[];
						relatedNodes: never[];
						impact: string;
						message: string;
					}[];
					all: never[];
					none: never[];
					impact: string;
					html: string;
					target: string[];
				}[];
		  }[]
		| undefined;
}): JSX.Element;
export const PagesEnum: Readonly<{
	Occurrence: number;
	Violation: number;
	Violations: number;
}>;
