import {Config} from 'metal-state';

export const pageStructure = Config.shapeOf(
	{
		description: Config.string(),
		rows: Config.arrayOf(
			Config.shapeOf(
				{
					columns: Config.arrayOf(
						Config.shapeOf(
							{
								fields: Config.array(),
								size: Config.number()
							}
						)
					)
				}
			)
		),
		title: Config.string()
	}
);

export const focusedFieldStructure = Config.shapeOf(
	{
		columnIndex: Config.number(),
		name: Config.string().required(),
		pageIndex: Config.number(),
		rowIndex: Config.number()
	}
);

export const rule = Config.shapeOf(
	{
		actions: Config.arrayOf(
			Config.shapeOf(
				{
					action: Config.string(),
					label: Config.string(),
					target: Config.string()
				}
			)
		),
		conditions: Config.arrayOf(
			Config.shapeOf(
				{
					operands: Config.arrayOf(
						Config.shapeOf(
							{
								label: Config.string(),
								repeatable: Config.bool(),
								type: Config.string(),
								value: Config.string()
							}
						)
					),
					operator: Config.string()
				}
			)
		),
		logicalOperator: Config.string()
	}
);