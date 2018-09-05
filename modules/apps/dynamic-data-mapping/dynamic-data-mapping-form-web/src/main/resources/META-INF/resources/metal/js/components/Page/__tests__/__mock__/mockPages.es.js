export default {
	rows: [
		{
			columns: [
				{
					fields: [
						{
							label: 'Radio',
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
							type: 'text'
						}
					],
					size: 12
				}
			]
		}
	],
	title: 'Untitled name'
};