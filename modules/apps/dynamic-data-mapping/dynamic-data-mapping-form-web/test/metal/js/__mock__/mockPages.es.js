export default [
	{
		rows: [
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'radio',
								label: 'Radio Field',
								options: [
									{
										label: 'Option 1',
										value: 'option1'
									},
									{
										label: 'Option 2',
										value: 'option2'
									}
								],
								settingsContext: {
									pages: []
								},
								type: 'radio'
							}
						],
						size: 3
					},
					{
						fields: [],
						size: 9
					}
				]
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'text1',
								label: 'Text Field 1',
								required: true,
								settingsContext: {
									pages: []
								},
								type: 'text'
							},
							{
								fieldName: 'text2',
								label: 'Text Field 2',
								required: true,
								settingsContext: {
									pages: []
								},
								type: 'text'
							}
						],
						size: 4
					},
					{
						fields: [
							{
								fieldName: 'select',
								label: 'Select Field',
								options: [
									{
										label: 'Option 1',
										value: 'option1'
									},
									{
										label: 'Option 2',
										value: 'option2'
									}
								],
								required: true,
								settingsContext: {
									pages: []
								},
								type: 'select'
							}
						],
						size: 6
					},
					{
						fields: [],
						size: 2
					}
				]
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'date',
								label: 'Date Field',
								required: true,
								settingsContext: {
									pages: []
								},
								type: 'date'
							}
						],
						size: 12
					}
				]
			}
		],
		title: 'Untitled name'
	}
];