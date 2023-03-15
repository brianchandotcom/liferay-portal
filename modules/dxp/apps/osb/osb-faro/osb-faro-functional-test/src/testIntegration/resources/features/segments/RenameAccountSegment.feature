@spira_Segments @Segments @Dynamic @team_FARO @priority_5 @blocked
Feature: Rename Accounts Segment
	As an Business User, I should be able to rename Accounts Segments

	Background: [Setup] Create a Static Accounts Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Individuals Segment" dropdown option
		* I create a criteria with the following conditions:
			| familyName | contains | west |
		* I name the Static segment "RenameAccountsSegment - ${Random.1}" and save it

	Scenario: Rename Accounts Segment
		Given I go to the "Segments" page
		And I click "RenameAccountsSegment - ${Random.1}" in the table
		When I click the "Edit Segment" button
		And I type "RenameAccountsSegment_EDITED - ${Random.1}" into the "Segment Name" input field
		And I click the "Save" button
		And I go to the "Segments" page
		And I search for "RenameAccountsSegment_EDITED - ${Random.1}"
		Then I should see a "Segment" named "RenameAccountsSegment_EDITED - ${Random.1}" with "5" items