AUI.add(
	'liferay-ddl-form-builder-modal',
	function(A) {
		var FormBuilderModal = A.Component.create(
			{
				AUGMENTS: [Liferay.DDL.FormBuilderModalSupport],

				EXTENDS: A.Modal,

				NAME: 'liferay-ddl-form-builder-modal'
			}
		);

		Liferay.namespace('DDL').FormBuilderModal = FormBuilderModal;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-modal-support']
	}
);