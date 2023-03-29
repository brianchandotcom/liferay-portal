@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_3
Feature: Duplicate a criteria
	As an Business User, I should be able to duplicate a Segment's Criteria

	Background: [Setup] Create a criteria
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
		  | familyName | contains | raynor |

	Scenario: Duplicate a criteria
		When I duplicate the criteria group in row 1
		Then I should see that criteria field is duplicated