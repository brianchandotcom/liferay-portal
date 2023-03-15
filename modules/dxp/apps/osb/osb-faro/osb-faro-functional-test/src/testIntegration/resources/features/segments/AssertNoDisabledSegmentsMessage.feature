@spira_Segments @Segments @team_FARO @priority_3
Feature: Inform the User when there are no disabled Segments
	As an Business User, I should see a message that there are no disabled
	segments if there are no disabled segments and I filter for them

	Background: [Setup] Navigate to the Segments Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page

	Scenario: Assert message when there are no disabled Segments
		When I click the "Filter and Order" button
		And I click the "Disabled Segments" dropdown option
		Then I should see a message that there are no "Segments" found
