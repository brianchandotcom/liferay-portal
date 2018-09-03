export default [
	{
		rows: [
			{
				columns: [
					{
						fields: [
							{
								label: 'Radio',
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
								label: 'Text',
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
								items: [
									{
										name: 'Foo'
									},
									{
										name: 'Bar'
									}
								],
								label: 'Select',
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
								label: 'Second row',
								required: true,
								settingsContext: {
									pages: []
								},
								type: 'text'
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