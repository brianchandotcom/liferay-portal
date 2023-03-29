@spira_Segments @Segments @Dynamic @Deletion @team_FARO @priority_3 @blocked
Feature: Delete a Disabled Segment with the Inline Delete Button
	As an Business User, I should see that I can delete a disabled Dynamic Segment with the inline delete button

	Background: [Setup] Create Dynamic Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "InlineDisabledSegmentDeletion - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle,myCustomField
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist,Some Property
			${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer, Another Prop
			"""
		* I click the "CSV File" button
		* I browse for a file named "InlineDisabledSegmentDeletion - ${Random.1}.csv"
		* I name the Data Source "InlineDisabledSegmentDeletion - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "1" fields not mapped
		* I click the "Select Analytics Cloud Field" dropdown
		* I click the "New Field" button
		* I type "InlineDisabledSegmentDeletion" into the "New Field Name" input field
		* I click the "Select" dropdown
		* I click the "Text" dropdown option from the dropdown overlay
		* I click the "Create" button
		* I click the "Done" button
		* I should see that "InlineDisabledSegmentDeletion - ${Random.1}" was successfully uploaded
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| InlineDisabledSegmentDeletion | is known |
		* I name the Dynamic segment "InlineDisabledSegmentDeletion - ${Random.1}" and save it

	Scenario: Delete a Disabled Segment with the Inline Delete Button
		Given I go to the "Data Source" page
		And I click "InlineDisabledSegmentDeletion - ${Random.1}" in the table
		And I click the "Delete Data Source" button
		And I delete the "InlineDisabledSegmentDeletion - ${Random.1}" Data Source
		When I go to the "Segments" page
		Then I should see an warning alert saying "Some of your segments are disabled because a data source has been removed."
		When I click the "View Disabled Segments" button
		And I click the inlined "Delete" button for the "InlineDisabledSegmentDeletion - ${Random.1}" row
		And I click the "Delete" button
		Then I should not see a segment named "InlineDisabledSegmentDeletion - ${Random.1}" in the table