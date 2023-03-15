@spira_Segments @Segments @Dynamic @team_FARO @priority_3
Feature: Cancel Creating a Dynamic Segment
	As an Business User, I should be able to cancel creating a Dynamic Segment

	Background: [Setup] Begin creating a Dynamic Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown

	Scenario: Cancel Creating a Dynamic Segment
		Given I create a criteria with the following conditions:
			| familyName | contains | raynor |
		And I name the segment "Cancel Segment"
		When I click "Segments" in the sidebar
		And I click the "Leave Page" button
		Then I should not see Segment named "Cancel Segment" in the table