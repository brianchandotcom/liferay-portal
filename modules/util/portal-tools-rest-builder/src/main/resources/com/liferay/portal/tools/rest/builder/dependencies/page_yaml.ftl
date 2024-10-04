Page${schemaName}:
	properties:
		actions:
			additionalProperties:
				additionalProperties:
					type: string
				type: object
			type: object
		facets:
			items:
				$ref: "#/components/schemas/Facet"
			type: array
		items:
			items:
				$ref: "#/components/schemas/${schemaName}"
			type: array
		lastPage:
			format: int64
			type: integer
		page:
			format: int64
			type: integer
		pageSize:
			format: int64
			type: integer
		totalCount:
			format: int64
			type: integer
	type: object