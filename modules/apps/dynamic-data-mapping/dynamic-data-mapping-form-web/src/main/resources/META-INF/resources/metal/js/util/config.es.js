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