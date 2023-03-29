@spira_Segments @Segments @Static @team_FARO @priority_3
Feature: Editor shows static segment label
	As a Business User, I should be able to see static segment label

	Background: [Setup] Navigate to Segments page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Editor shows static segment label
		Given I click the "Create Segment" button
		When I click the "Static Segment" dropdown option
		Then I should see editor label is "Static Segment"