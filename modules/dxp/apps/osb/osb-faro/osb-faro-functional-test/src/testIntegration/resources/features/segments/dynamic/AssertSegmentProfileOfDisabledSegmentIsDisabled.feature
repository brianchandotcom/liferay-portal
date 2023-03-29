@spira_Segments @Segments @Deletion @team_FARO @priority_4 @blocked
Feature: Assert Segment Profile of a Disabled Segment is Disabled
	As an Business User, I should see that the Segment Profile of a disabled Segment is disabled

	Background: [Setup] Create Dynamic Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "AssertSegmentProfileOfDisabledSegmentIsDisabled - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle,myCustomField
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist,Some Property
			${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer, Another Prop
			"""
		* I click the "CSV File" button
		* I browse for a file named "AssertSegmentProfileOfDisabledSegmentIsDisabled - ${Random.1}.csv"
		* I name the Data Source "AssertSegmentProfileOfDisabledSegmentIsDisabled - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "1" fields not mapped
		* I click the "Select Analytics Cloud Field" dropdown
		* I click the "New Field" button
		* I type "AssertSegmentProfileOfDisabledSegmentIsDisabledField" into the "New Field Name" input field
		* I click the "Select" dropdown
		* I click the "Text" dropdown option from the dropdown overlay
		* I click the "Create" button
		* I click the "Done" button
		* I should see that "AssertSegmentProfileOfDisabledSegmentIsDisabled - ${Random.1}" was successfully uploaded
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| AssertSegmentProfileOfDisabledSegmentIsDisabledField | is known |
		* I name the Dynamic segment "AssertSegmentProfileOfDisabledSegmentIsDisabled - ${Random.1}" and save it

	Scenario: Assert that the Segment Profile is disabled
		Given I go to the "Data Source" page
		And I click "AssertSegmentProfileOfDisabledSegmentIsDisabled - ${Random.1}" in the table
		And I click the "Delete Data Source" button
		And I delete the "AssertSegmentProfileOfDisabledSegmentIsDisabled - ${Random.1}" Data Source
		When I go to the "Segments" page
		And I click "AssertSegmentProfileOfDisabledSegmentIsDisabled - ${Random.1}" in the table
		Then I should see an error alert saying "This segment is disabled because some criteria has been affected by removal of a data source. To continue using this segment please update the criteria."