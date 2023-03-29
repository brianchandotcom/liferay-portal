@spira_Search @Search @Segments @List @team_FARO @priority_4 @blocked
Feature: Filter by Disabled Segments
	As an Business User, I should be able to filter by disablde segments using the filter menu

	Background: [Setup] Create Disabled Dynamic Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "FilterByDisabledSegments - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle,myCustomField
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist,Some Property
			${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer, Another Prop
			"""
		* I click the "CSV File" button
		* I browse for a file named "FilterByDisabledSegments - ${Random.1}.csv"
		* I name the Data Source "FilterByDisabledSegments - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "1" fields not mapped
		* I click the "Select Analytics Cloud Field" dropdown
		* I click the "New Field" button
		* I type "FilterByDisabledSegmentsField" into the "New Field Name" input field
		* I click the "Select" dropdown
		* I click the "Text" dropdown option from the dropdown overlay
		* I click the "Create" button
		* I click the "Done" button
		* I should see that "FilterByDisabledSegments - ${Random.1}" was successfully uploaded
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| FilterByDisabledSegmentsField | is known |
		* I name the Dynamic segment "FilterByDisabledSegments - ${Random.1}" and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| givenName | is | blossom |
		* I name the Dynamic segment "FilterByDisabledSegments (Not Disabled) - ${Random.1}" and save it
		* I go to the "Data Source" page
		* I click "FilterByDisabledSegments - ${Random.1}" in the table
		* I click the "Delete Data Source" button
		* I delete the "FilterByDisabledSegments - ${Random.1}" Data Source

	Scenario: Filter by Disabled Segments
		Given I go to the "Segments" page
		And I should see a segment named "FilterByDisabledSegments (Not Disabled) - ${Random.1}" in the table
		When I click the "Filter and Order" button
		And I click the "Disabled Segments" dropdown option
		Then I should see a segment named "FilterByDisabledSegments - ${Random.1}" in the table
		And I should not see a segment named "FilterByDisabledSegments (Not Disabled) - ${Random.1}" in the table