@spira_Segments @Segments @Profile @team_FARO @priority_3
Feature: Segment Overview distribution filtered by text
	As a Business User, I should be able to filter segment overview by text property

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Distribution card filtered by text
		Given I click "everybody" in the table
		When I create breakdown context "Individuals" by "gender" named Gender
		Then I should see the following names in "Gender" breakdown:
			| male 	 |
			| female |
		And I delete the breakdown