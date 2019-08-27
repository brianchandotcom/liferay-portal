window.AlloyEditor = {
	Selections: [
		{
			buttons: ['linkEdit'],
			name: 'link'
		},
		{
			buttons: [
				'styles',
				'bold',
				'italic',
				'underline',
				'link',
				'twitter'
			],
			name: 'text'
		}
	]
};

window.AUI = () => ({
	use: (...modules) => {
		const callback = modules[modules.length - 1];

		callback({
			LiferayAlloyEditor: () => ({
				render: () => ({
					destroy: () => {},
					getHTML: () => 'test',
					getNativeEditor: () => ({
						on: () => true,
						setData: () => false
					})
				})
			}),
			one: () => ({
				innerHTML: () => {}
			})
		});
	}
});

window.themeDisplay = window.Liferay.ThemeDisplay;
