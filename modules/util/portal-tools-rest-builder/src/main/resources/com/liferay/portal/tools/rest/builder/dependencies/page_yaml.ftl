Page${schemaName}:
	properties:
		actions:
			type: object
			additionalProperties:
				type: object
				additionalProperties:
					type: string
		facets:
			type: array
			items:
				$ref: '#/components/schemas/Facet'
		items:
			type: array
			items:
				$ref: '#/components/schemas/${schemaName}'
		lastPage:
			type: integer
			format: int64
		pageSize:
			type: integer
			format: int64
		page:
			type: integer
			format: int64
		totalCount:
			type: integer
			format: int64
	type: object