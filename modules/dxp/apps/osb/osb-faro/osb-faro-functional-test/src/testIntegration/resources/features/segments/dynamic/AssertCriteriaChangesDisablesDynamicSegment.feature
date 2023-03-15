@spira_Segments @Segments @Dynamic @team_FARO @priority_5 @blocked
Feature: Dynamic Segments with criteria changes are disabled
	As an Business User, I should see that Dynamic Segments with criteria changes are disabled

	Background: [Setup] Create Dynamic Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "AssertCriteriaChangesDisablesDynamicSegment - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle,myCustomField
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist,Some Property
			${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer, Another Prop
			"""
		* I click the "CSV File" button
		* I browse for a file named "AssertCriteriaChangesDisablesDynamicSegment - ${Random.1}.csv"
		* I name the Data Source "AssertCriteriaChangesDisablesDynamicSegment - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "1" fields not mapped
		* I click the "Select Analytics Cloud Field" dropdown
		* I click the "New Field" button
		* I type "AssertCriteriaChangesDisablesDynamicSegmentField" into the "New Field Name" input field
		* I click the "Select" dropdown
		* I click the "Text" dropdown option from the dropdown overlay
		* I click the "Create" button
		* I click the "Done" button
		* I should see that "AssertCriteriaChangesDisablesDynamicSegment - ${Random.1}" was successfully uploaded
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| AssertCriteriaChangesDisablesDynamicSegmentField | is known |
		* I name the Dynamic segment "AssertCriteriaChangesDisablesDynamicSegment - ${Random.1}" and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| givenName | is | blossom |
		* I name the Dynamic segment "AssertCriteriaChangesDisablesDynamicSegment (Not Disabled) - ${Random.1}" and save it

	Scenario: Assert Dynamic Segments with criteria changes are disabled
		Given I go to the "Data Source" page
		And I click "AssertCriteriaChangesDisablesDynamicSegment - ${Random.1}" in the table
		And I click the "Delete Data Source" button
		And I delete the "AssertCriteriaChangesDisablesDynamicSegment - ${Random.1}" Data Source
		When I go to the "Segments" page
		Then I should see a warning alert saying "Some of your segments are disabled because a data source has been removed. "
		And I should see that "AssertCriteriaChangesDisablesDynamicSegment (Not Disabled) - ${Random.1}" is not disabled
		When I click the "View Disabled Segments" button
		And I should see that "AssertCriteriaChangesDisablesDynamicSegment - ${Random.1}" is disabled
		And I should not see a segment named "AssertCriteriaChangesDisablesDynamicSegment (Not Disabled) - ${Random.1}" in the table