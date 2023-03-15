@spira_Segments @Segments @Dynamic @team_FARO @priority_4 @blocked
Feature: Missing Criteria are highlighted when editing a disabled Segment
	As an Business User, I should see that a missing Criteria is highlighted when editing a disabled Segment

	Background: [Setup] Create Dynamic Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "AssertMissingCriteriaHighlight - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle,myCustomField${Random.1}
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist,Some Property
			${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer, Another Prop
			"""
		* I click the "CSV File" button
		* I browse for a file named "AssertMissingCriteriaHighlight - ${Random.1}.csv"
		* I name the Data Source "AssertMissingCriteriaHighlight - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "1" fields not mapped
		* I click the "Select Analytics Cloud Field" dropdown
		* I click the "New Field" button
		* I type "AssertMissingCriteriaHighlightField" into the "New Field Name" input field
		* I click the "Select" dropdown
		* I click the "Text" dropdown option from the dropdown overlay
		* I click the "Create" button
		* I click the "Done" button
		* I should see that "AssertMissingCriteriaHighlight - ${Random.1}" was successfully uploaded
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create an OR criteria with the following conditions:
			| givenName                           | contains | blossom |
			| AssertMissingCriteriaHighlightField | is known |         |
		* I name the Dynamic segment "AssertMissingCriteriaHighlight - ${Random.1}" and save it

	Scenario: Assert missing Criteria is highlighted
		Given I go to the "Data Source" page
		And I click "AssertMissingCriteriaHighlight - ${Random.1}" in the table
		And I click the "Delete Data Source" button
		And I delete the "AssertMissingCriteriaHighlight - ${Random.1}" Data Source
		And I go to the "Segments" page
		And I should see a warning alert saying "Some of your segments are disabled because a data source has been removed. "
		And I click the "View Disabled Segments" button
		When I click the inlined "Edit" button for the "AssertMissingCriteriaHighlight - ${Random.1}" row
		Then I should see an error alert saying "Some criteria are empty, please update to continue using this segment."
		And I should see that a criteria field is missing
