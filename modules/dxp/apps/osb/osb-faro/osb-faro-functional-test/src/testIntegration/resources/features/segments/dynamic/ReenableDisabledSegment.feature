@spira_Segments @Segments @team_FARO @priority_4 @blocked
Feature: Re-enable Disabled Segment
	As an Business User, I should be able to inline edit a disabled segment so that it is re-enabled

	Background: [Setup] Create Dynamic Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "ReenableDisabledSegment - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle,myCustomField${Random.1}
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist,Some Property
			${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer, Another Prop
			"""
		* I click the "CSV File" button
		* I browse for a file named "ReenableDisabledSegment - ${Random.1}.csv"
		* I name the Data Source "ReenableDisabledSegment - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "1" fields not mapped
		* I click the "Select Analytics Cloud Field" dropdown
		* I click the "New Field" button
		* I type "ReenableDisabledSegmentField" into the "New Field Name" input field
		* I click the "Select" dropdown
		* I click the "Text" dropdown option from the dropdown overlay
		* I click the "Create" button
		* I click the "Done" button
		* I should see that "ReenableDisabledSegment - ${Random.1}" was successfully uploaded
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I click the "Individual Attributes" dropdown option
		* I create an OR criteria with the following conditions:
			| familyName                   | contains | raynor |
			| ReenableDisabledSegmentField | is known |         |
		* I name the Dynamic segment "ReenableDisabledSegment - ${Random.1}" and save it

	Scenario: Inline edit a disabled Segment to re-enable it
		Given I go to the "Data Source" page
		And I click "ReenableDisabledSegment - ${Random.1}" in the table
		And I click the "Delete Data Source" button
		And I delete the "ReenableDisabledSegment - ${Random.1}" Data Source
		And I go to the "Segments" page
		And I should see a warning alert saying "Some of your segments are disabled because a data source has been removed. "
		And I click the "View Disabled Segments" button
		When I click the inlined "Edit" button for the "ReenableDisabledSegment - ${Random.1}" row
		And I delete the criteria group in row "1"
		And I click the "Save Segment" button
		And I go to the "Segments" page
		And I search for "ReenableDisabledSegment - ${Random.1}"
		Then I should see a "Segment" named "ReenableDisabledSegment - ${Random.1}" with "2" items