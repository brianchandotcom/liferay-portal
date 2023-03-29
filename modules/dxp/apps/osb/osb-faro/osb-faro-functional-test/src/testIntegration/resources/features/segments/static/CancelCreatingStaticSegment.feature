@spira_Segments @Segments @Static @team_FARO @priority_3
Feature: Cancel creating a static segment
	As a Business User, I should be able to cancel creating a static segment

	Background: [Setup] Navigate to Segments page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Cancel static segment
		Given I click the "Create Segment" button
		And I click the "Static Segment" dropdown option
		And I click the "Add Members" button
		And I click the checkbox on the table row containing "Abram Bauch"
		And I click the "Add" button
		And I name the segment "Cancel Segment"
		When I click the "Cancel" button
		And I click the "Leave Page" button
		Then I go to the "Segments" page
		And I should not see Segment named "Cancel Segment" in the table