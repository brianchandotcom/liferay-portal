export default [{
    title: 'Untitled name',
    rows: [
        {
            columns: [
                {
                    fields: [
                        {
                            type: 'radio',
                            label: 'Radio'
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
                            type: 'text',
                            label: 'Text',
                            required: true,
                        }
                    ],
                    size: 4
                },
                {
                    fields: [
                        {
                            type: 'select',
                            label: 'Select',
                            required: true,
                            items: [
                                {
                                    name: 'Foo'
                                },
                                {
                                    name: 'Bar'
                                }
                            ]
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
                            type: 'text',
                            label: 'Second row',
                            required: true
                        }
                    ],
                    size: 12
                }
            ]
        }
    ]
}];